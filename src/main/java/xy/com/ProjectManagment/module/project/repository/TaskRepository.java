package xy.com.ProjectManagment.module.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xy.com.ProjectManagment.module.project.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query("SELECT ub FROM UserBoard ub " +
            "JOIN ub.task t " +
            "WHERE t.id = :userId")
    Optional<List<Task>> getTaskByUserId(@Param("userId") Long userId);

    Optional<Task> findByTitle(String title);
    @Query(value = "SELECT t.* FROM user_data AS u " +
            "INNER JOIN user_board_task AS ubt ON u.r_id = ubt.user_board_id " +
            "INNER JOIN task AS t ON ubt.task_id = t.id " +
            "WHERE u.username = :username", nativeQuery = true)
    Optional<List<Task>> findTasksByUsername(@Param("username") String username);
}
