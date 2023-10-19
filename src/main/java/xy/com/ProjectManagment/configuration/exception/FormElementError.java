package xy.com.ProjectManagment.configuration.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormElementError {
    private String fieldName;
    private String errorMessage;
}
