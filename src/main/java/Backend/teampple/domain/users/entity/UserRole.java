package Backend.teampple.domain.users.entity;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public enum UserRole {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private String role;

    UserRole(String role) {
        this.role=role;
    }
}
