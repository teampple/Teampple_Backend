package Backend.teampple.domain.users.service;

import Backend.teampple.domain.users.repository.UserRepository;
import Backend.teampple.domain.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public String getTasks(String refreshToken) {
        return "";
    }

    public String getFeedbacks(String refreshToken) {
        return "";
    }
}
