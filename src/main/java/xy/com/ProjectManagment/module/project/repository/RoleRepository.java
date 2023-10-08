package xy.com.ProjectManagment.module.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xy.com.ProjectManagment.module.project.entity.UserRole;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<UserRole, Integer> {
    Optional<UserRole> findByTitle(String title);
}
