package xy.com.ProjectManagment.module.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xy.com.ProjectManagment.module.project.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

}
