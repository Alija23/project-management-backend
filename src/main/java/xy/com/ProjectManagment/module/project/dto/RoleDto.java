package xy.com.ProjectManagment.module.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xy.com.ProjectManagment.module.project.entity.UserRole;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private String title;

    public RoleDto(UserRole role) {
        title = role.getTitle();
    }
}
