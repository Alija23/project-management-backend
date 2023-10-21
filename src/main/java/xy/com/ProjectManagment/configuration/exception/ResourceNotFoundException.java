package xy.com.ProjectManagment.configuration.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    List<FormElementError> formElementError;
    public ResourceNotFoundException(String message, List<FormElementError> formElementError) {
        super(message);
        this.formElementError = formElementError;
    }
}
