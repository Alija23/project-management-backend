package xy.com.ProjectManagment.module.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xy.com.ProjectManagment.module.project.entity.UserData;

import java.util.List;
import java.util.Optional;

public interface UserDataRepository extends JpaRepository<UserData, Integer> {

    Optional<UserData> findByUsername(String username);
    Optional<UserData> findByEmail(String email);
    @Query(value = "SELECT * FROM user_data " +
            "INNER JOIN user_role ON user_data.role_id = user_role.id ",
            nativeQuery = true)
    Optional<List<UserData>> getAllUserData();

}
