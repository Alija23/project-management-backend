package xy.com.ProjectManagment.module.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xy.com.ProjectManagment.module.project.entity.Task;
import xy.com.ProjectManagment.module.project.entity.UserData;
import xy.com.ProjectManagment.module.project.entity.UserRole;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDataDto {
    private Number rId;
    private String username;
    private String email;
    private RoleDto role;
    public UserDataDto(UserData userData) {
        rId = userData.getId();
        username = userData.getUsername();
        email = userData.getEmail();
        role = new RoleDto(userData.getUserRole());
    }

}
