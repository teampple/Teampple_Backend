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
import java.util.Map;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final OAuth2AttributeUserMapper oAuth2AttributeUserMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("엥");
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("엥");
        log.info("{}",userRequest.getClientRegistration());
        log.info("{}",userRequest.getAccessToken().getTokenValue());
        log.info("{}",userRequest.getAdditionalParameters());
        log.info(String.valueOf(oAuth2User));
        Map<String, Object> attributes = oAuth2User.getAttributes();
//        /**provider 정보*/
//        String registrationId = userRequest.getClientRegistration().getRegistrationId();
//
//        /**userInfoEndPoint*/
//        ClientRegistration.ProviderDetails.UserInfoEndpoint userInfoEndpoint = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint();
//
//        OAuth2Attribute oAuth2Attribute =
//                OAuth2Attribute.of(registrationId, userInfoEndpoint.getUserNameAttributeName(), oAuth2User.getAttributes());
//
//        /**회원 가입 여부 확인*/
//        if (!userRepository.existsByKakaoId(oAuth2Attribute.getOAuthId())) {
//            User user = oAuth2AttributeUserMapper.toDto(oAuth2Attribute);
//            userRepository.save(user);
//        }

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


        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), attributes, "id");
    }
}
