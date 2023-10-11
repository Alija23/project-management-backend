package xy.com.ProjectManagment.module.project.service;

import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import xy.com.ProjectManagment.configuration.exception.FormElementError;
import xy.com.ProjectManagment.configuration.exception.ResourceNotFoundException;
import xy.com.ProjectManagment.configuration.exception.UserFormInputException;
import xy.com.ProjectManagment.module.project.dto.UserDataDto;
import xy.com.ProjectManagment.module.project.entity.UserData;
import xy.com.ProjectManagment.module.project.entity.UserRole;
import xy.com.ProjectManagment.module.project.repository.RoleRepository;
import xy.com.ProjectManagment.module.project.repository.UserDataRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDataServiceImpl implements UserDataService, UserDetailsService {
    private UserDataRepository userDataRepository;
    private BCryptPasswordEncoder encoder;
    private RoleServiceImpl roleService;
    public UserDataServiceImpl(
            UserDataRepository userDataRepository,
            RoleServiceImpl roleService,
            BCryptPasswordEncoder encoder
    ) {
        this.userDataRepository = userDataRepository;
        this.encoder = encoder;
        this.roleService = roleService;
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
    public UserDataDto saveUser(UserData userData) {
        userData.setPassword(encoder.encode(userData.getPassword()));
        UserRole userRole = roleService.getUserRoleByTitle(userData.getUserRole().getTitle());
        userData.setUserRole(userRole);
        try {
            userDataRepository.save(userData);
        } catch (DataIntegrityViolationException exception) {
            List<FormElementError> formElementError = new ArrayList<>();
            ConstraintViolationException cause = (ConstraintViolationException) exception.getCause();
            String failedField = cause.getConstraintName();
            formElementError.add(new FormElementError(failedField, failedField + "must be unique"));

            throw new UserFormInputException("some error ocured", formElementError);
        }

        UserDataDto userDataDto = new UserDataDto(userData);
        return userDataDto;
    }
}
