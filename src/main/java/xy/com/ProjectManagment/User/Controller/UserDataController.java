package xy.com.ProjectManagment.User.Controller;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xy.com.ProjectManagment.User.Entity.UserData;
import xy.com.ProjectManagment.User.Repository.UserDataRepository;
import xy.com.ProjectManagment.User.Service.UserDataServiceImpl;

import java.util.Optional;

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
