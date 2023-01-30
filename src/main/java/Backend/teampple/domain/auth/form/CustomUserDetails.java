package Backend.teampple.domain.auth.form;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * customUserDetail -> Authentication 내부에 들어있는 User 객체 값
 * userName = userEntity 를 조회할 수 있는 uniqueKey
 */
@Getter
public class CustomUserDetails implements UserDetails {
    private final String id;
    private final String password;
    private final boolean isDeleted;
    private final Collection<GrantedAuthority> authorities;

    @Builder
    public CustomUserDetails(String id, String password, boolean isDeleted, Collection<GrantedAuthority> authorities) {
        this.id = id;
        this.password = password;
        this.isDeleted = isDeleted;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isDeleted;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !isDeleted;
    }
}
