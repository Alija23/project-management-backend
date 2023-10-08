package xy.com.ProjectManagment.module.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xy.com.ProjectManagment.module.project.entity.UserData;
import xy.com.ProjectManagment.module.project.entity.UserRole;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDataDto {
    private String username;
    private String email;
    private String role;

    public UserDataDto(UserData userData) {
        username = userData.getUsername();
        email = userData.getEmail();
        role = userData.getUserRole().getTitle();
    }
}
