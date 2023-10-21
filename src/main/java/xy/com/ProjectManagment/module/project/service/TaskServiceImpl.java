package xy.com.ProjectManagment.module.project.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import xy.com.ProjectManagment.configuration.exception.FormElementError;
import xy.com.ProjectManagment.configuration.exception.ResourceNotFoundException;
import xy.com.ProjectManagment.configuration.exception.UserFormInputException;
import xy.com.ProjectManagment.module.project.dto.TaskDto;
import xy.com.ProjectManagment.module.project.entity.Task;
import xy.com.ProjectManagment.module.project.entity.UserBoard;
import xy.com.ProjectManagment.module.project.entity.UserData;
import xy.com.ProjectManagment.module.project.repository.TaskRepository;
import xy.com.ProjectManagment.module.project.repository.UserBoardRepository;
import xy.com.ProjectManagment.module.project.repository.UserDataRepository;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService{
    private UserDataRepository userDataRepository;
    private TaskRepository taskRepository;
    private UserBoardRepository userBoardRepository;
    public TaskServiceImpl(UserBoardRepository userBoardRepository, UserDataRepository userDataRepository, TaskRepository taskRepository) {
        this.userDataRepository = userDataRepository;
        this.taskRepository = taskRepository;
        this.userBoardRepository = userBoardRepository;
    }
    @Override
    public List<Task> getAllUserTask(String username) {
        List<FormElementError> formElementError = new ArrayList<>();
        Optional<UserData> userData = userDataRepository.findByUsername(username);
        if (userData.isPresent()) {
            Optional<List<Task>> task = taskRepository.findTasksByUsername(userData.get().getUsername());
            if (task.isPresent()) {
                return task.get();
            }
        } else {
            FormElementError exception = new FormElementError("username", "notFound");
            formElementError.add(exception);
            throw new ResourceNotFoundException("User not found", formElementError);
        }


        return new ArrayList<>();
    }

    @Override
    @Transactional
    public void deleteTask(String title) {
        Optional<Task> task = taskRepository.findByTitle(title);
        List<FormElementError> formElementError = new ArrayList<>();
        if (task.isPresent()) {
            taskRepository.delete(task.get());
            return;
        }
        FormElementError exception = new FormElementError("title", "notFound");
        formElementError.add(exception);
        throw new ResourceNotFoundException("Task hasnt been found", formElementError);
    }

    @Override
    @Transactional
    public void updateTask(String title, TaskDto taskDto) {
        Optional<Task> task = taskRepository.findByTitle(title);
        if (task.isPresent()) {
            task.get().setTitle(taskDto.getTitle());
            task.get().setDescription(taskDto.getDescription());
            taskRepository.save(task.get());
            return;
        }
        List<FormElementError> formElementError = new ArrayList<>();
        FormElementError exception = new FormElementError("title", "notFound");
        formElementError.add(exception);
        throw new ResourceNotFoundException("Task not found", formElementError);

    }

    @Override
    @Transactional
    public Optional<Task> createTask(TaskDto taskDto) {
        Optional<Task> task = taskRepository.findByTitle(taskDto.getTitle());
        if (task.isPresent()) {
            List<FormElementError> formElementErrors = new ArrayList<>();
            FormElementError exception = new FormElementError("title", "alreadyExist");
            formElementErrors.add(exception);
            throw new UserFormInputException("Already exist", formElementErrors);
        }
        Task task1 = new Task(taskDto.getTitle(), taskDto.getDescription());
        taskRepository.save(task1);
        return task;
    }

    @Override
    @Transactional
    public void addTaskToUser(String username, String title) {
        Optional<UserData> userData = userDataRepository.findByUsername(username);
        List<FormElementError> formElementErrors = new ArrayList<>();
        if (userData.isEmpty()) {
            FormElementError exception = new FormElementError("username", "notFound");
            formElementErrors.add(exception);
        }
        Optional<Task> task = taskRepository.findByTitle(title);
        if (task.isEmpty()) {
            FormElementError exception = new FormElementError("title", "notFound");
            formElementErrors.add(exception);
            throw new UserFormInputException("Already exist", formElementErrors);
        }
        Optional<UserBoard> userBoard = userBoardRepository.findById(userData.get().getId());
        userBoard.get().getTask().add(task.get());
        userBoardRepository.save(userBoard.get());
    }
}
