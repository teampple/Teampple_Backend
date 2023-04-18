package Backend.teampple.domain.auth.security;

import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.entity.UserProfile;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@Getter
public class UserAdapter extends CustomUserDetails {
//    private final UserProfile userProfile;

    public UserAdapter(User user) {
        super(user, Collections.singleton(new SimpleGrantedAuthority(user.getUserRole().getRole())));
//        this.userProfile = user.getUserProfile();
    }
}
