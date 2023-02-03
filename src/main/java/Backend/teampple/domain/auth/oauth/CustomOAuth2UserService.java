package Backend.teampple.domain.auth.oauth;

import Backend.teampple.domain.auth.security.CustomUserDetails;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.entity.UserProfile;
import Backend.teampple.domain.users.repository.UserRepository;
import Backend.teampple.domain.users.service.UserProfileService;
import Backend.teampple.domain.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
//TODO: 자동화 및 provider 처리 필요
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserService userService;
    private final UserProfileService userProfileService;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info(String.valueOf(oAuth2User));
        Map<String, Object> attributes = oAuth2User.getAttributes();
        /**provider 정보*/
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        /**userInfoEndPoint*/
        ClientRegistration.ProviderDetails.UserInfoEndpoint userInfoEndpoint = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint();

//        OAuth2Attribute oAuth2Attribute =
//                OAuth2Attribute.of(registrationId, userInfoEndpoint.getUserNameAttributeName(), oAuth2User.getAttributes());

        /**oauth 속 개인정보
         * 추후 변경 예정 -> attribute
         * */
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        log.info((String) properties.get("nickname"));

        User user = null;
        /**회원 가입 여부 확인 및 RefreshToken update*/
        if (!userRepository.existsByKakaoId(oAuth2User.getName())) {
            UserProfile profile = userProfileService.createProfile((String)  properties.get("nickname"));
            user = userService.createUser(profile, oAuth2User.getName());
        } else {
            user = userRepository.findByKakaoId(oAuth2User.getName()).orElseThrow();
        }

//        -------------response 값------------
//        id=2643611141,
//        connected_at=2023-01-30T06:32:16Z,
//        properties={nickname=정상훈},
//        kakao_account={profile_nickname_needs_agreement=false,
//                profile={nickname=정상훈},
//                has_email=true,
//                email_needs_agreement=false,
//                is_email_valid=true,
//                is_email_verified=true,
//                email=wjdtkdgns329@naver.com}}]


//        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), attributes, "id");
        return new CustomUserDetails(user, attributes, Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
