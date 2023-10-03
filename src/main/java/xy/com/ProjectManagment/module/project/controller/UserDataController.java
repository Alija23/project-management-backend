package xy.com.ProjectManagment.module.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xy.com.ProjectManagment.module.project.entity.UserData;
import xy.com.ProjectManagment.module.project.service.UserDataServiceImpl;

@RestController
@RequestMapping("/api/user-data")
public class UserDataController {
    private UserDataServiceImpl userDataService;
    public UserDataController(UserDataServiceImpl userDataService) {
        this.userDataService = userDataService;
    }

    @GetMapping("/profile")
    UserData getUserData() {
        UserData userData = new UserData();
        userData.setUsername("dilo");
        userData.setEmail("lodi");
        userData.setPassword("kurac");
//       userData = userDataService.loadUserByUsername(username);
        return userData;
    }
}
