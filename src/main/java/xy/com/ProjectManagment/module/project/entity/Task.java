package xy.com.ProjectManagment.module.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="task")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="title", unique = true)
    private String title;

    @Column(name="description")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH, CascadeType.REFRESH}
    )
    @JoinTable(
            name="user_board_task",
            joinColumns=@JoinColumn(name="task_id"),
            inverseJoinColumns = @JoinColumn(name="user_board_id")
    )
    private List<UserBoard> userBoard;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
