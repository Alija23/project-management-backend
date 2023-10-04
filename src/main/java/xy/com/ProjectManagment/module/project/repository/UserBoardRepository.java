package xy.com.ProjectManagment.module.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xy.com.ProjectManagment.module.project.entity.UserBoard;

public interface UserBoardRepository extends JpaRepository<UserBoard, Integer> {
}
