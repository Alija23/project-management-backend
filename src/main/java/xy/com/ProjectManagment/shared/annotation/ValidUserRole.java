package xy.com.ProjectManagment.shared.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserRoleValidator.class)
public @interface ValidUserRole {
    String message() default "Invalid UserRole";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}