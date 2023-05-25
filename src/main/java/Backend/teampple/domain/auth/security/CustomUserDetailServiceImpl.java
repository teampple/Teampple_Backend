package Backend.teampple.domain.auth.security;

import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.repository.UserRepository;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
/**
 * Spring Security 에서 사용자 정보를 담는 인터페이스
 * */
public class CustomUserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username);
        User user = userRepository.findByAuthKey(username)
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.INVALID_TOKEN));
//        return new CustomUserDetails(user,Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        return new UserAdapter(user);
    }
}
