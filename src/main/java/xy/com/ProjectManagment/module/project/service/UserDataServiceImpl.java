package xy.com.ProjectManagment.module.project.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import xy.com.ProjectManagment.configuration.exception.ErrorMessage;
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
import xy.com.ProjectManagment.module.project.repository.RoleRepository;
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
            FormElementError exception = new FormElementError("username", "notFound");
            formElementError.add(exception);
            throw new ResourceNotFoundException("User with username: " + username + " not found", formElementError);
        } catch (ResourceNotFoundException ex) {
           UserData userData1 = new UserData();
           userData1.setUsername("");
           userData1.setPassword(encoder.encode("dsagsd"));
           return userData1;
        }
        }

        return userData.get();
    }

    @Override
    @Transactional
    public UserDataDto saveUser(RegisterModel registerModel) {
        UserRole userRole = roleService.getUserRoleByTitle(registerModel.getUserRole().getTitle());
        registerModel.setUserRole(userRole);
        UserBoard userBoard = new UserBoard();
        List<FormElementError> formElementError = new ArrayList<>();
        UserData userData = new UserData(registerModel);

        try {
            Optional<UserData> tmpUserData = userDataRepository.findByUsername(registerModel.getUsername());
            if (tmpUserData.isPresent()) {
                FormElementError exception = new FormElementError("username", "duplicate");
                formElementError.add(exception);
            }
            tmpUserData = Optional.empty();
            tmpUserData = userDataRepository.findByEmail(registerModel.getEmail());
            if (tmpUserData.isPresent()) {
                FormElementError exception = new FormElementError("email", "duplicate");
                formElementError.add(exception);
            }
            if (!Objects.equals(registerModel.getPassword(), registerModel.getConfirmPassword())) {
                FormElementError exception = new FormElementError("password", "notSame");
                formElementError.add(exception);
                throw new UserFormInputException("err", formElementError);
            }
            registerModel.setPassword(encoder.encode(registerModel.getPassword()));
            userBoard.setUserData(userData);
            userData.setPassword(encoder.encode(userData.getPassword()));
            userDataRepository.save(userData);
            userBoardRepository.save(userBoard);
        } catch (DataIntegrityViolationException exception) {
            throw new UserFormInputException("some error ocured", formElementError);
        }

        UserDataDto userDataDto = new UserDataDto(userData);
        return userDataDto;
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


}
