package xy.com.ProjectManagment.module.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xy.com.ProjectManagment.module.project.dto.UserDataDto;
import xy.com.ProjectManagment.module.project.entity.Task;
import xy.com.ProjectManagment.module.project.entity.UserData;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDataBoardTaskModel {

    private UserDataDto userDataDto;
    private List<Task> task;

    public UserDataBoardTaskModel(UserData data, List<Task> task) {
        userDataDto = new UserDataDto(data);
        this.task = task;
    }
}
