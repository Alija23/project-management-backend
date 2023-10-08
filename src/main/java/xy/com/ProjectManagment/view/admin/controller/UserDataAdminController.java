package xy.com.ProjectManagment.view.admin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xy.com.ProjectManagment.configuration.exception.ResourceNotFoundException;
import xy.com.ProjectManagment.module.project.dto.UserDataDto;
import xy.com.ProjectManagment.module.project.entity.UserData;
import xy.com.ProjectManagment.module.project.model.RegisterModel;
import xy.com.ProjectManagment.module.project.service.UserDataServiceImpl;

import java.util.Optional;

@RestController
@RequestMapping("/api/user-data-admin")
public class UserDataAdminController {
    private UserDataServiceImpl userDataService;
    public UserDataAdminController(UserDataServiceImpl userDataService) {
        this.userDataService = userDataService;
    }
    @PostMapping("/save-admin")
    public ResponseEntity<UserDataDto> saveAdmin(@RequestBody RegisterModel adminModel) {
       UserData userData = new UserData(adminModel);
       userDataService.saveUser(userData);
       UserDataDto userDataDto = new UserDataDto(userData);
       return ResponseEntity.ok(userDataDto);
    }

    @GetMapping("/get-admin")
    public ResponseEntity<UserDataDto> getAdmin() {
        UserData userData = userDataService.loadUserByUsername("diloee");
        UserDataDto userDataDto = new UserDataDto(userData);
        return new ResponseEntity<>(userDataDto, HttpStatus.OK);
   }
}
