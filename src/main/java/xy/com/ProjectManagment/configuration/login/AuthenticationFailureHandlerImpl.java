package xy.com.ProjectManagment.configuration.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import xy.com.ProjectManagment.configuration.exception.FormElementError;
import xy.com.ProjectManagment.configuration.exception.ResourceNotFoundException;
import xy.com.ProjectManagment.configuration.exception.UserFormInputException;
import xy.com.ProjectManagment.module.project.dto.UserDataDto;
import xy.com.ProjectManagment.module.project.entity.UserData;
import xy.com.ProjectManagment.module.project.repository.UserDataRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Getter
@Setter
@AllArgsConstructor
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    private ObjectMapper objectMapper;
    private UserDataRepository userDataRepository;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        List<FormElementError> formElementError = new ArrayList<>();
        Optional<UserData> userData = userDataRepository.findByUsername(request.getParameter("username"));
        if (userData.isEmpty() && !Objects.equals(request.getParameter("password"), "")) {
            FormElementError ex = new FormElementError("username", "Invalid username");
            formElementError.add(ex);
        } else if (userData.isPresent() && !Objects.equals(userData.get().getPassword(), request.getParameter("password"))) {
            FormElementError ex1 = new FormElementError("password", "Invalid password");
            formElementError.add(ex1);
        } else {
            FormElementError ex = new FormElementError("username", "Invalid username");
            formElementError.add(ex);
            FormElementError ex1 = new FormElementError("password", "Invalid password");
            formElementError.add(ex1);
        }
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        String objectJackson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(formElementError);
        response.getWriter().write(objectJackson);
        response.flushBuffer();
    }
}
