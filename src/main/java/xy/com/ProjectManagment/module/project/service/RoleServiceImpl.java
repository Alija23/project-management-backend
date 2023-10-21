package xy.com.ProjectManagment.module.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;
import xy.com.ProjectManagment.configuration.exception.FormElementError;
import xy.com.ProjectManagment.configuration.exception.UserFormInputException;
import xy.com.ProjectManagment.module.project.entity.UserRole;
import xy.com.ProjectManagment.module.project.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{
    public RoleRepository roleRepository;
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Override
    public UserRole getUserRoleByTitle(String title) {
        Optional<UserRole> userRole = roleRepository.findByTitle(title);
        if (userRole.isEmpty()) {
            List<FormElementError> formElementErrors = new ArrayList<>();
            FormElementError exception = new FormElementError("title", "badTitle");
            formElementErrors.add(exception);
            throw  new UserFormInputException("Role muss be ADMIN OR USER", formElementErrors);
        }
       return userRole.get();
    }
}
