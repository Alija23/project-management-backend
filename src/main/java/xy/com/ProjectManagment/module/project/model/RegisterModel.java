package xy.com.ProjectManagment.module.project.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xy.com.ProjectManagment.module.project.entity.UserRole;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterModel {
    private String username;

    private String email;

    private String password;

    private String confirmPassword;

    private UserRole userRole;


}
