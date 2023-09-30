package xy.com.ProjectManagment.User.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xy.com.ProjectManagment.User.Entity.UserData;

public interface UserDataRepository extends JpaRepository<UserData, Integer> {

}
