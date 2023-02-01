package Backend.teampple.domain.auth.form;

import Backend.teampple.domain.users.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

/**
 * customUserDetail -> Authentication 내부에 들어있는 User 객체 값
 * userName = userEntity 를 조회할 수 있는 uniqueKey
 */
@Getter
public class CustomUserDetails<OAuth2UserInfo> implements UserDetails, OAuth2User {

    private final User user;
    private final Collection<GrantedAuthority> authorities;
    private OAuth2UserInfo oAuth2UserInfo;

//    public CustomUserDetails(User user, Collection<GrantedAuthority> authorities) {
//        this.user = user;
//        this.authorities = authorities;
//    }

    @Builder
    public CustomUserDetails(User user, OAuth2UserInfo oAuth2UserInfo, Collection<GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return "user.password";
    }

    @Override
    public String getUsername() {
        return "user.id";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isDeleted();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !user.isDeleted();
    }

    @Override
    public String getName() {
        return null;
    }
}
