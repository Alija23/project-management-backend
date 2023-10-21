package xy.com.ProjectManagment.shared;

import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import xy.com.ProjectManagment.module.project.entity.Task;
import xy.com.ProjectManagment.module.project.entity.UserBoard;
import xy.com.ProjectManagment.module.project.entity.UserData;
import xy.com.ProjectManagment.module.project.entity.UserRole;
import xy.com.ProjectManagment.module.project.model.RegisterModel;
import xy.com.ProjectManagment.module.project.repository.UserBoardRepository;
import xy.com.ProjectManagment.module.project.repository.UserDataRepository;
import xy.com.ProjectManagment.module.project.service.UserDataServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommandLinerRunnerImpl implements CommandLineRunner {

    private UserDataRepository userDataRepository;
    private UserBoardRepository userBoardRepository;
    private BCryptPasswordEncoder encoder;
    public CommandLinerRunnerImpl(
            UserBoardRepository userBoardRepository,
            UserDataRepository userDataRepository,
            BCryptPasswordEncoder encoder
    ) {
        this.userDataRepository = userDataRepository;
        this.encoder = encoder;
        this.userBoardRepository = userBoardRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        UserData userData = new UserData();
        userData.setUsername("dilo");
        userData.setEmail("dilo2304@live.com");
        userData.setPassword("test123");
        UserRole userRole = new UserRole();
        userRole.setTitle("ADMIN");
        userData.setUserRole(userRole);
        UserBoard userBoard = new UserBoard();
        userBoard.setUserData(userData);
        Task task = new Task();
        task.setTitle("GLAVNI BAJA");
        task.setDescription("super admin radi sta hoces");
        Task task1 = new Task();
        task1.setTitle("BAJA");
        task1.setDescription("hoces");
        List<Task> listTask = new ArrayList<>();
        listTask.add(task1);
        listTask.add(task);
        userBoard.setTask(listTask);
        userDataRepository.save(userData);
        userBoardRepository.save(userBoard);
    }
}