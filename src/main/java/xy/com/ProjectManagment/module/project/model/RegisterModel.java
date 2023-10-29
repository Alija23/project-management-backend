package xy.com.ProjectManagment.module.project.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xy.com.ProjectManagment.module.project.entity.UserRole;
import xy.com.ProjectManagment.shared.annotation.PasswordMatches;
import xy.com.ProjectManagment.shared.annotation.UniqueEmail;
import xy.com.ProjectManagment.shared.annotation.UniqueUsername;
import xy.com.ProjectManagment.shared.annotation.ValidUserRole;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
public class RegisterModel {
    @NotEmpty(message = "Username is required")
    @UniqueUsername(message = "Username is already in use")
    private String username;

    @Email(message = "Email is not valid")
    @UniqueEmail(message = "Email is already in use")
    @NotEmpty(message = "Email is required")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    @NotEmpty(message = "Password is required")
    private String password;

    @NotEmpty(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String confirmPassword;

    @ValidUserRole(message = "Invalid role")
    private UserRole userRole;


}
