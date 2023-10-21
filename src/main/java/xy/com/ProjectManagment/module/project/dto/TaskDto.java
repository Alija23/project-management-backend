package xy.com.ProjectManagment.module.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xy.com.ProjectManagment.module.project.entity.Task;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private String title;
    private String description;
    public TaskDto(Task singleTask) {
       title = singleTask.getTitle();
       description = singleTask.getDescription();
    }
}
