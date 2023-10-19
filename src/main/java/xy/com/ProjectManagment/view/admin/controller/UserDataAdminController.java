package xy.com.ProjectManagment.view.admin.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xy.com.ProjectManagment.configuration.exception.ResourceNotFoundException;
import xy.com.ProjectManagment.module.project.dto.UserDataDto;
import xy.com.ProjectManagment.module.project.entity.UserData;
import xy.com.ProjectManagment.module.project.model.RegisterModel;
import xy.com.ProjectManagment.module.project.model.UserDataBoardTaskModel;
import xy.com.ProjectManagment.module.project.repository.UserDataRepository;
import xy.com.ProjectManagment.module.project.service.UserDataServiceImpl;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-data-admin")
public class UserDataAdminController {
    private UserDataServiceImpl userDataService;
    private UserDataRepository userDataRepository;
    public UserDataAdminController(UserDataServiceImpl userDataService, UserDataRepository userDataRepository) {
        this.userDataService = userDataService;
        this.userDataRepository = userDataRepository;
    }
    @PostMapping("/save-admin")
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

    @GetMapping("/get")
    public ResponseEntity<UserData> get(@RequestParam String username) {

    Optional<UserData> userData =     userDataRepository.findByUsername(username);
        return ResponseEntity.ok(userData.get());
    }
}
