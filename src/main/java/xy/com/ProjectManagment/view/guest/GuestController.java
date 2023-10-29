package xy.com.ProjectManagment.view.guest;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xy.com.ProjectManagment.configuration.exception.FormElementError;
import xy.com.ProjectManagment.configuration.exception.UserFormInputException;
import xy.com.ProjectManagment.module.project.dto.UserDataDto;
import xy.com.ProjectManagment.module.project.entity.UserData;
import xy.com.ProjectManagment.module.project.model.RegisterModel;
import xy.com.ProjectManagment.module.project.repository.UserDataRepository;
import xy.com.ProjectManagment.module.project.service.TaskServiceImpl;
import xy.com.ProjectManagment.module.project.service.UserDataServiceImpl;
import xy.com.ProjectManagment.shared.annotation.PasswordMatches;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/guest")
public class GuestController {

    private UserDataServiceImpl userDataService;
    private UserDataRepository userDataRepository;
    private TaskServiceImpl taskService;
    public GuestController(TaskServiceImpl taskService, UserDataServiceImpl userDataService, UserDataRepository userDataRepository) {
        this.userDataService = userDataService;
        this.userDataRepository = userDataRepository;
        this.taskService = taskService;
    }
    @PostMapping("/save-user")
    public ResponseEntity<UserDataDto> createAccount(@Valid @RequestBody RegisterModel userModel, BindingResult bindingResult) {
        userDataService.createAccount(userModel, bindingResult);
        UserData userData = new UserData(userModel);
        UserDataDto userDataDto = new UserDataDto(userData);
        return ResponseEntity.ok(userDataDto);
    }

}
