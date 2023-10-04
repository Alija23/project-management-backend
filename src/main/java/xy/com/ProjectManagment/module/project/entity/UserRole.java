package xy.com.ProjectManagment.module.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user_role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="title")
    private String title;

    @OneToMany(
            mappedBy="userRole",
            cascade =
                    {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH, CascadeType.REFRESH}
    )
    private List<UserData> userData;

    public void add(UserData tmpUserData) {
        if (userData == null) {
            userData = new ArrayList<>();
        }
        userData.add(tmpUserData);
        tmpUserData.setUserRole(this);
    }
    // role ima relaciju sa userdata one to many
}
