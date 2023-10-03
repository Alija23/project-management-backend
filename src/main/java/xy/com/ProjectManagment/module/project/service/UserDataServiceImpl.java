package xy.com.ProjectManagment.module.project.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xy.com.ProjectManagment.module.project.entity.UserData;
import xy.com.ProjectManagment.module.project.repository.UserDataRepository;

import java.util.Optional;

@Service
public class UserDataServiceImpl implements UserDataService, UserDetailsService {
    private UserDataRepository userDataRepository;

    public UserDataServiceImpl(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
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

    public void save(UserData userData) {
        userDataRepository.save(userData);
    }
}
