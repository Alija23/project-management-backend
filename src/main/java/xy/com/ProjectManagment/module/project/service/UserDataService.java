package xy.com.ProjectManagment.module.project.service;

import xy.com.ProjectManagment.module.project.dto.UserDataDto;
import xy.com.ProjectManagment.module.project.entity.UserData;
import xy.com.ProjectManagment.module.project.model.RegisterModel;
import xy.com.ProjectManagment.module.project.model.UserDataBoardTaskModel;

import java.util.List;

public interface UserDataService {
    UserDataDto saveUser(RegisterModel userData);
    List<UserDataBoardTaskModel> getAllUserDataRoleTask();

    void deleteUserDataByUsername(String username);
    UserDataBoardTaskModel updateUser(UserDataBoardTaskModel userDataBoardTaskModel);
}
