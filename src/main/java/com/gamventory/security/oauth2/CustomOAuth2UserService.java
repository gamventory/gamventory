package com.gamventory.security.oauth2;

import com.gamventory.entity.BaseEntity;
import com.gamventory.entity.Member;
import com.gamventory.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
//    private PasswordEncoder passwordEncoder;


    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        log.info("registrationId : "+registrationId);

        log.info(oAuth2User.toString());

        String name = "";
        String email = "";
        String socialId = "";

        if(registrationId.equals("google")) {

            name = oAuth2User.getAttribute("name");
            email = oAuth2User.getAttribute("email");
            socialId = oAuth2User.getAttribute("id");
        } else if(registrationId.equals("naver")) {

            Map<String, Object> response = oAuth2User.getAttribute("response");
            name = (String)response.get("name");
            email = (String)response.get("email");
            socialId = (String)response.get("id");
        } else if(registrationId.equals("kakao")) {

        }



        log.info("name : " + name);
        log.info("email : " + email);
        log.info("id : " + socialId);

        Member member = memberRepository.findByEmail(email);

        if(member == null) {
            // 존재하지 않을 경우, 새로운 멤버로 등록
            member = Member.createSocialMember(name, email, registrationId, oAuth2User.getName());
            memberRepository.save(member);
        } else if(member.getSocialProvider() == null || member.getSocialId() == null) {
            // 기존 회원이라면, 소셜 로그인 정보 업데이트
            member.setSocialProvider(registrationId);
            member.setSocialId(socialId);
            memberRepository.save(member);
        }

        return new CustomOAuth2User(oAuth2User, member);
    }
}