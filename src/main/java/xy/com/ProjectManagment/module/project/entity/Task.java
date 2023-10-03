package xy.com.ProjectManagment.module.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="task")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="r_id")
    private int id;

    @Column(name="taskname")
    private String taskname;

    @Column(name="description")
    private String description;

    @Column(name="userboard")
    private String userboard;

}
