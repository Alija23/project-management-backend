package xy.com.ProjectManagment.module.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xy.com.ProjectManagment.module.project.entity.UserData;

import java.util.Optional;

public interface UserDataRepository extends JpaRepository<UserData, Integer> {

    Optional<UserData> findByUsername(String username);
}
