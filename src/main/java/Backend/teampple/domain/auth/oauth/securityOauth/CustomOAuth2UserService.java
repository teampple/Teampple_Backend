package Backend.teampple.domain.auth.oauth.securityOauth;

import Backend.teampple.domain.auth.mapper.OAuth2AttributeUserMapper;
import Backend.teampple.domain.auth.oauth.OAuth2Attribute;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final OAuth2AttributeUserMapper oAuth2AttributeUserMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info(String.valueOf(oAuth2User));

        /**provider 정보*/
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        /**userInfoEndPoint*/
        ClientRegistration.ProviderDetails.UserInfoEndpoint userInfoEndpoint = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint();

        OAuth2Attribute oAuth2Attribute =
                OAuth2Attribute.of(registrationId, userInfoEndpoint.getUserNameAttributeName(), oAuth2User.getAttributes());

        /**회원 가입 여부 확인*/
        if (!userRepository.existsByKakaoId(oAuth2Attribute.getOAuthId())) {
            User user = oAuth2AttributeUserMapper.toDto(oAuth2Attribute);
            userRepository.save(user);
        }

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), oAuth2Attribute.convertToMap(), "id");
    }
}
