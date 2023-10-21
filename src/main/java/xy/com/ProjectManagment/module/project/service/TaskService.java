package xy.com.ProjectManagment.module.project.service;

import xy.com.ProjectManagment.module.project.dto.TaskDto;
import xy.com.ProjectManagment.module.project.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> getAllUserTask(String username);
    void deleteTask(String title);
    void updateTask(String title, TaskDto taskDto);
    Optional<Task> createTask(TaskDto taskDto);

    void addTaskToUser(String username, String title);
}
