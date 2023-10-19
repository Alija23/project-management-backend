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
}
