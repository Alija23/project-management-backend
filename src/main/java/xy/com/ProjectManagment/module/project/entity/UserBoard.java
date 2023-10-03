package xy.com.ProjectManagment.module.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="user_board")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBoard {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="r_id")
    private int id;

    @Column(name="userdata")
    private String userdata;

    @Column(name="task")
    private String task;
}
