package xy.com.ProjectManagment.shared;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import xy.com.ProjectManagment.module.project.entity.UserData;
import xy.com.ProjectManagment.module.project.service.UserDataServiceImpl;

@Component
public class CommandLinerRunnerImpl implements CommandLineRunner {

    private UserDataServiceImpl userDataService;
    public CommandLinerRunnerImpl(UserDataServiceImpl userDataService) {
        this.userDataService = userDataService;
    }

    @Override
    public void run(String... args) throws Exception {
        UserData userData = new UserData();
        userData.setUsername("dilo");
        userData.setEmail("dilo2304@live.com");
        userData.setPassword("test123");
        userDataService.save(userData);

    }
}