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
import xy.com.ProjectManagment.module.project.dto.UserDataDto;

import java.io.IOException;
@Component
@Getter
@Setter
@AllArgsConstructor
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    private ObjectMapper objectMapper;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        UserDataDto userDataDto = new UserDataDto();
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        String username = request.getParameter("username");
        userDataDto.setUsername(username);
        String objectJackson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userDataDto);
        response.getWriter().write(objectJackson);
        response.flushBuffer();
    }
}
