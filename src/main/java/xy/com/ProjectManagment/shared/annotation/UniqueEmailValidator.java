
package xy.com.ProjectManagment.shared.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import xy.com.ProjectManagment.module.project.repository.UserDataRepository;


@AllArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private UserDataRepository userDataRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return userDataRepository.findByEmail(email).isEmpty();
    }
}
