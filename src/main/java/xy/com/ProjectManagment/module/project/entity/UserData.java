package xy.com.ProjectManagment.module.project.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xy.com.ProjectManagment.module.project.model.RegisterModel;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name="user_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class UserData implements UserDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="r_id")
    private int id;

    @Column(name="username", unique = true)
    private String username;

    @Column(name="email", unique = true)
    private String email;

    @Column(name="password")
    private String password;

    @ManyToOne(cascade =
            {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH, CascadeType.REFRESH}
    )
    @JoinColumn(name="role_id")
    private UserRole userRole;

    public UserData(RegisterModel registerModel) {
        setUsername(registerModel.getUsername());
        setEmail(registerModel.getEmail());
        setPassword(registerModel.getPassword());
        setUserRole(registerModel.getUserRole());
   }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userRole.getTitle()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
