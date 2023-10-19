package xy.com.ProjectManagment.module.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="user_board")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBoard {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @OneToOne
    @JoinColumn(name="user_data_id")
    private UserData userData;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH, CascadeType.REFRESH}
    )
    @JoinTable(
            name="user_board_task",
            joinColumns=@JoinColumn(name="user_board_id"),
            inverseJoinColumns = @JoinColumn(name="task_id")
    )
    private List<Task> task;



}
