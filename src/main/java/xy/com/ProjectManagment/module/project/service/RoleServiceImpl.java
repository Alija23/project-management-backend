package xy.com.ProjectManagment.module.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;
import xy.com.ProjectManagment.module.project.entity.UserRole;
import xy.com.ProjectManagment.module.project.repository.RoleRepository;

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
        if (userRole.isPresent()) {
            return userRole.get();
        }
        UserRole userRole1 = new UserRole();
        userRole1.setTitle(title);
        return userRole1;
    }
}
