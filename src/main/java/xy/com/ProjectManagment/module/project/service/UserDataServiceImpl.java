package xy.com.ProjectManagment.module.project.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;
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
public class UserDataServiceImpl implements UserDataService, UserDetailsService {
    private UserDataRepository userDataRepository;
    private UserBoardRepository userBoardRepository;
    private BCryptPasswordEncoder encoder;
    private RoleServiceImpl roleService;
    private TaskRepository taskRepository;
    public UserDataServiceImpl(
            UserDataRepository userDataRepository,
            RoleServiceImpl roleService,
            BCryptPasswordEncoder encoder,
            UserBoardRepository userBoardRepository,
            TaskRepository taskRepository
    ) {
        this.userDataRepository = userDataRepository;
        this.encoder = encoder;
        this.roleService = roleService;
        this.userBoardRepository = userBoardRepository;
        this.taskRepository = taskRepository;
    }
    @Override
    public UserData loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserData> userData;
        userData = userDataRepository.findByUsername(username);
        if (userData.isEmpty()) {
            throw new ResourceNotFoundException("User with username: " + username + " doesnt exist");
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


}
