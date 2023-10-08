package xy.com.ProjectManagment.module.project.service;

import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import xy.com.ProjectManagment.module.project.dto.UserDataDto;
import xy.com.ProjectManagment.module.project.entity.UserData;
import xy.com.ProjectManagment.module.project.entity.UserRole;
import xy.com.ProjectManagment.module.project.repository.RoleRepository;
import xy.com.ProjectManagment.module.project.repository.UserDataRepository;

import java.util.Optional;

@Service
public class UserDataServiceImpl implements UserDataService, UserDetailsService {
    private UserDataRepository userDataRepository;
    private BCryptPasswordEncoder encoder;
    private RoleServiceImpl roleService;
    public UserDataServiceImpl(
            UserDataRepository userDataRepository,
            RoleServiceImpl roleService,
            BCryptPasswordEncoder encoder
    ) {
        this.userDataRepository = userDataRepository;
        this.encoder = encoder;
        this.roleService = roleService;
    }
    @Override
    public UserData loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserData> userData;
        userData = userDataRepository.findByUsername(username);
        if (userData.isEmpty()) {
            throw new UsernameNotFoundException("User with username: " + username + " doesnt exist");
        }
        return userData.get();
    }

    @Override
    @Transactional
    public UserDataDto saveUser(UserData userData) {
        userData.setPassword(encoder.encode(userData.getPassword()));
        UserRole userRole = roleService.getUserRoleByTitle(userData.getUserRole().getTitle());
        userData.setUserRole(userRole);
        userDataRepository.save(userData);
        UserDataDto userDataDto = new UserDataDto(userData);
        return userDataDto;
    }
}
