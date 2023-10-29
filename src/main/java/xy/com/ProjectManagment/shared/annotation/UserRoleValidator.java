package xy.com.ProjectManagment.shared.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import xy.com.ProjectManagment.module.project.entity.UserRole;

import java.lang.annotation.Annotation;

public class UserRoleValidator implements ConstraintValidator<ValidUserRole, UserRole> {

    @Override
    public void initialize(ValidUserRole constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserRole role, ConstraintValidatorContext context) {
        return role.getTitle() != null && (role.getTitle().equals("ADMIN") || role.getTitle().equals("USER"));
    }
}
