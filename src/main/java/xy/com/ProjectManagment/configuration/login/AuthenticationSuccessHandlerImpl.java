package xy.com.ProjectManagment.configuration.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import xy.com.ProjectManagment.module.project.dto.RoleDto;
import xy.com.ProjectManagment.module.project.dto.UserDataDto;
import xy.com.ProjectManagment.module.project.entity.UserData;

import javax.management.relation.Role;
import java.io.IOException;
@Component
@Getter
@Setter
@AllArgsConstructor
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDataDto userDataDto = new UserDataDto();
        response.setStatus(HttpServletResponse.SC_OK);
        Object principalUserData = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserData userData = (UserData) principalUserData;
        userDataDto.setUsername(userData.getUsername());
        userDataDto.setRId(userData.getId());
        userDataDto.setEmail(userData.getEmail());
        userDataDto.setRole(new RoleDto(userData.getUserRole()));
        String objectJackson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userDataDto);
        response.getWriter().write(objectJackson);
        response.flushBuffer();
    }
}
