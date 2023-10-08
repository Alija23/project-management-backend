package xy.com.ProjectManagment.module.project.service;

import xy.com.ProjectManagment.module.project.dto.UserDataDto;
import xy.com.ProjectManagment.module.project.entity.UserData;

public interface UserDataService {
    public UserDataDto saveUser(UserData userData);
}
