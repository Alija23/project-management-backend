package xy.com.ProjectManagment.shared.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import xy.com.ProjectManagment.module.project.model.RegisterModel;

import java.util.Objects;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegisterModel> {

    @Override
    public boolean isValid(RegisterModel registerModel, ConstraintValidatorContext context) {
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(registerModel);

        String password = (String) wrapper.getPropertyValue("password");
        String confirmPassword = (String) wrapper.getPropertyValue("confirmPassword");

        return Objects.equals(password, confirmPassword);
    }
}
