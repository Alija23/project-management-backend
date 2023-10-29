package xy.com.ProjectManagment.view.admin.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xy.com.ProjectManagment.configuration.exception.FormElementError;
import xy.com.ProjectManagment.configuration.exception.ResourceNotFoundException;
import xy.com.ProjectManagment.module.project.dto.TaskDto;
import xy.com.ProjectManagment.module.project.dto.UserDataDto;
import xy.com.ProjectManagment.module.project.entity.Task;
import xy.com.ProjectManagment.module.project.entity.UserData;
import xy.com.ProjectManagment.module.project.model.RegisterModel;
import xy.com.ProjectManagment.module.project.model.UserDataBoardTaskModel;
import xy.com.ProjectManagment.module.project.repository.UserDataRepository;
import xy.com.ProjectManagment.module.project.service.TaskService;
import xy.com.ProjectManagment.module.project.service.TaskServiceImpl;
import xy.com.ProjectManagment.module.project.service.UserDataServiceImpl;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-data-admin")
public class UserDataAdminController {
    private UserDataServiceImpl userDataService;
    private UserDataRepository userDataRepository;
    private TaskServiceImpl taskService;
    public UserDataAdminController(TaskServiceImpl taskService, UserDataServiceImpl userDataService, UserDataRepository userDataRepository) {
        this.userDataService = userDataService;
        this.userDataRepository = userDataRepository;
        this.taskService = taskService;
    }
    @PostMapping("/save-user")
    public ResponseEntity<UserDataDto> saveAdmin(@RequestBody RegisterModel adminModel) {
          userDataService.saveUser(adminModel);
          UserData userData = new UserData(adminModel);
          UserDataDto userDataDto = new UserDataDto(userData);
          return ResponseEntity.ok(userDataDto);
      }

    @GetMapping("/get-admin")
    public ResponseEntity<List<UserDataBoardTaskModel>> getAdmin() {
        List<UserDataBoardTaskModel> userData = userDataService.getAllUserDataRoleTask();
        return new ResponseEntity<>(userData, HttpStatus.OK);
   }


    @DeleteMapping("/delete-admin")
    public ResponseEntity<String> deleteUserData(@RequestParam String username) {
        userDataService.deleteUserDataByUsername(username);
        return ResponseEntity.ok("User data with username " + username + " has been deleted");
    }

    @PutMapping("/update-admin")
    public ResponseEntity<UserDataBoardTaskModel> updateUserData(@RequestBody UserDataBoardTaskModel userDataBoardTaskModel) {
        userDataService.updateUser(userDataBoardTaskModel);
        return ResponseEntity.ok(userDataBoardTaskModel);
    }

    @GetMapping("/get-task")
    public ResponseEntity<List<TaskDto>> getAllUserTask(@RequestParam String username) {
        List<Task> task = taskService.getAllUserTask(username);
        if (task.isEmpty()) {
            List<TaskDto> taskDto = new ArrayList<>();
            return ResponseEntity.ok(taskDto);
        }
        List<TaskDto> taskDtoList = new ArrayList<>();
            for (Task singleTask : task) {
                TaskDto taskDto1 = new TaskDto(singleTask);
                taskDtoList.add(taskDto1);
            }
        return ResponseEntity.ok(taskDtoList);
    }

    @DeleteMapping("/delete-task")
    public ResponseEntity<String> deleteTask(@RequestParam String title) {
        taskService.deleteTask(title);
        return ResponseEntity.ok("Task has been successfully deleted");
    }

    @PutMapping("/update-task")
    public ResponseEntity<TaskDto> updateTask(@RequestParam String title, @RequestBody TaskDto task) {
        taskService.updateTask(title, task);
        return ResponseEntity.ok(task);
    }

    @PostMapping("/create-task")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        taskService.createTask(taskDto);
        return ResponseEntity.ok(taskDto);
    }

    @PutMapping("add-task-to-user")
    public ResponseEntity<String> addTaskToUser(@RequestParam("username") String username, @RequestParam("title") String title) {
        taskService.addTaskToUser(username, title);
        return ResponseEntity.ok("Task with title " + title + " has been successfully added to user with username " + username);
    }
}
