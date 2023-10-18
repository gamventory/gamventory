package com.gamventory.service;

import com.gamventory.dto.MemberUpdateFormDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.gamventory.entity.Member;
import com.gamventory.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public MemberUpdateFormDto getMember(String email) {

       Member member  = memberRepository.findByEmail(email);

       log.info("--------- get member --------");
       log.info(member.toString());
       MemberUpdateFormDto memberUpdateForm = MemberUpdateFormDto.builder()
               .name(member.getName())
               .email(member.getEmail())
               .address(member.getStreetAddress())
               .build();

        return memberUpdateForm;
    }

    public Member saveMember(Member member) {

        validateDulpicateMember(member);

        return memberRepository.save(member);
    }

    public Long modifyMember(MemberUpdateFormDto memberUpdateForm) {

        Member member = memberRepository.findByEmail(memberUpdateForm.getEmail());
        member.modifyMember(memberUpdateForm);
        memberRepository.save(member);

        return member.getId();
    }

    private void validateDulpicateMember(Member member) {

        Member findMember = memberRepository.findByEmail(member.getEmail());

        if(findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email);

        if(member == null) {
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
            .username(member.getEmail())
            .password(member.getPassword())
            .roles(member.getRole().toString())
            .build();
    }


}
