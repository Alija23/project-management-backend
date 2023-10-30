package xy.com.ProjectManagment.module.project.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import xy.com.ProjectManagment.configuration.exception.FormElementError;
import xy.com.ProjectManagment.configuration.exception.ResourceNotFoundException;
import xy.com.ProjectManagment.configuration.exception.UserFormInputException;
import xy.com.ProjectManagment.module.project.dto.UserDataDto;
import xy.com.ProjectManagment.module.project.entity.Task;
import xy.com.ProjectManagment.module.project.entity.UserBoard;
import xy.com.ProjectManagment.module.project.entity.UserData;
import xy.com.ProjectManagment.module.project.entity.UserRole;
import xy.com.ProjectManagment.module.project.model.RegisterModel;
import xy.com.ProjectManagment.module.project.model.UserDataBoardTaskModel;
import xy.com.ProjectManagment.module.project.repository.TaskRepository;
import xy.com.ProjectManagment.module.project.repository.UserBoardRepository;
import xy.com.ProjectManagment.module.project.repository.UserDataRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDataServiceImpl implements UserDataService, UserDetailsService {
    private UserDataRepository userDataRepository;
    private UserBoardRepository userBoardRepository;
    private BCryptPasswordEncoder encoder;
    private RoleServiceImpl roleService;
    private TaskRepository taskRepository;
    @Override
    public UserData loadUserByUsername(String username) throws UsernameNotFoundException {
        List<FormElementError> formElementError = new ArrayList<>();
        Optional<UserData> userData = userDataRepository.findByUsername(username);
        if (userData.isEmpty()) {
        try {
            throw new UserFormInputException("User with username: " + username + " not found", formElementError);
        } catch (UserFormInputException ex) {
           UserData userData1 = new UserData();
           userData1.setUsername("");
           userData1.setPassword(encoder.encode("dsagsd"));
           return userData1;
        }
        }

        return userData.get();
    }

    @Override
    public List<UserDataBoardTaskModel> getAllUserDataRoleTask() {
        List<UserDataBoardTaskModel> userDataBoardTaskModels = new ArrayList<>();
        Optional<List<UserData>> userData = userDataRepository.getAllUserData();
        if (userData.isPresent()) {
            List<UserData> userDataList = userData.get();

            for (UserData data: userDataList) {
                Optional<List<Task>> task = taskRepository.getTaskByUserId((long)data.getId());
                UserDataBoardTaskModel model = new UserDataBoardTaskModel(data, task.get());
                userDataBoardTaskModels.add(model);
            }
        } else {
            // Handle the case where the Optional is empty
            System.out.println("Optional is empty");
        }

        return userDataBoardTaskModels;
    }

    @Override
    @Transactional
    public void deleteUserDataByUsername(String username) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);
        if (userData.isPresent()) {
            UserData userData1 = userData.get();
            userBoardRepository.deleteById(userData1.getId());
            userDataRepository.delete(userData1);
        }
    }

    @Override
    @Transactional
    public UserDataBoardTaskModel updateUser(UserDataBoardTaskModel userDataBoardTaskModel) {
        Optional<UserData> userData = userDataRepository.findByUsername(userDataBoardTaskModel.getUserDataDto().getUsername());
        Optional<UserBoard> userBoard = userBoardRepository.findById(userData.get().getId());
        if (userData.isEmpty()) {
            throw new UsernameNotFoundException("user not found");
        }
        UserData userData1 = userData.get();
        if (userData1.getUsername() != userDataBoardTaskModel.getUserDataDto().getUsername()) {
            userData1.setUsername(userDataBoardTaskModel.getUserDataDto().getUsername());
        }
        if (userData1.getEmail() != userDataBoardTaskModel.getUserDataDto().getEmail()) {
            userData1.setEmail(userDataBoardTaskModel.getUserDataDto().getEmail());
        }
        if (userData1.getUserRole().getTitle() != userDataBoardTaskModel.getUserDataDto().getRole().getTitle()) {
            userData1.getUserRole().setTitle(userDataBoardTaskModel.getUserDataDto().getRole().getTitle());
        }
        userDataRepository.save(userData1);

        for (Task task: userDataBoardTaskModel.getTask()) {
            Optional<Task> tmpTask = taskRepository.findByTitle(task.getTitle());
            if (tmpTask.isPresent()) {
                tmpTask.get().setDescription(task.getDescription());
                taskRepository.save(tmpTask.get());
            }
        }

        return userDataBoardTaskModel;
    }

    @Override
    @Transactional
    public UserDataDto createAccount(@Valid RegisterModel registerModel, BindingResult bindingResult) {
        checkForErrors(registerModel, bindingResult);
        UserData userData = new UserData(registerModel);
        UserRole userRole = roleService.getUserRoleByTitle(registerModel.getUserRole().getTitle());
        userData.setUserRole(userRole);
        UserBoard userBoard = new UserBoard();
        userBoard.setUserData(userData);
        userData.setPassword(encoder.encode(registerModel.getPassword()));
        userDataRepository.save(userData);
        userBoardRepository.save(userBoard);
        UserDataDto userDataDto = new UserDataDto(userData);
        return userDataDto;
    }
    void checkForErrors(RegisterModel registerModel, BindingResult bindingResult) {
        List<FormElementError> listFormElementError = new ArrayList<>();
        //check for password
        if (!Objects.equals(registerModel.getConfirmPassword(), registerModel.getPassword())) {
            FormElementError formElementError = new FormElementError();
            formElementError.setFieldName("password");
            formElementError.setErrorMessage("Passwords do not match");
            listFormElementError.add(formElementError);
        }
        // check privileges
        Object principalUserData = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principalUserData instanceof UserData) && Objects.equals(registerModel.getUserRole().getTitle(), "ADMIN")) {
            FormElementError formElementError1 = new FormElementError();
            formElementError1.setFieldName("userRole");
            formElementError1.setErrorMessage("You don't have admin privileges");
            listFormElementError.add(formElementError1);
        } else if (principalUserData instanceof UserData){
            UserData userData = (UserData) principalUserData;
            if (
                    Objects.equals(registerModel.getUserRole().getTitle(), "ADMIN") &&
                            !Objects.equals(userData.getUserRole().getTitle(), "ADMIN")

            ) {
                FormElementError formElementError1 = new FormElementError();
                formElementError1.setFieldName("userRole");
                formElementError1.setErrorMessage("You don't have admin privileges");
                listFormElementError.add(formElementError1);
            }
        }

        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                FormElementError formElementError = new FormElementError();
                formElementError.setFieldName(fieldError.getField());
                formElementError.setErrorMessage(fieldError.getDefaultMessage());
                listFormElementError.add(formElementError);
            }
        }
        if (!listFormElementError.isEmpty()) {
            throw new UserFormInputException("error", listFormElementError);
        }
    }
}
