package xy.com.ProjectManagment.module.project.service;

import xy.com.ProjectManagment.module.project.entity.UserRole;

import java.util.Optional;

public interface RoleService {
    UserRole getUserRoleByTitle(String title);
}
