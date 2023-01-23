package Backend.teampple.global.common.validation.dto;

import Backend.teampple.domain.tasks.entity.Task;
import Backend.teampple.domain.users.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserTaskDto {
    private User user;
    private Task task;

    @Builder
    public UserTaskDto(User user, Task task) {
        this.user = user;
        this.task = task;
    }
}
