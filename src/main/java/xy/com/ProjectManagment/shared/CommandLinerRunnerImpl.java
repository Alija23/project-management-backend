package xy.com.ProjectManagment.shared;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import xy.com.ProjectManagment.module.project.entity.UserData;
import xy.com.ProjectManagment.module.project.entity.UserRole;
import xy.com.ProjectManagment.module.project.service.UserDataServiceImpl;

@Component
public class CommandLinerRunnerImpl implements CommandLineRunner {

    private UserDataServiceImpl userDataService;
    private BCryptPasswordEncoder encoder;
    public CommandLinerRunnerImpl(UserDataServiceImpl userDataService, BCryptPasswordEncoder encoder) {
        this.userDataService = userDataService;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {
        UserData userData = new UserData();
        userData.setUsername("dilo");
        userData.setEmail("dilo2304@live.com");
        userData.setPassword(encoder.encode("test123"));
        UserRole userRole = new UserRole();
        userRole.setTitle("ADMIN");
        userData.setUserRole(userRole);
        userDataService.saveUser(userData);

    }
}