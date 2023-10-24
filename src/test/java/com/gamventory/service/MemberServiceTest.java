package com.gamventory.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.gamventory.dto.MemberFormDto;
import com.gamventory.entity.Member;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberServiceTest {
    
    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(){

        MemberFormDto member = MemberFormDto.builder()
                .name("test")
                .email("test@gmail.com")
                .password("1234")
                .build();
        
        return Member.createMember(member, passwordEncoder);
    }


//    @Test
//    @DisplayName("회원 가입 테스트")
//    public void saveMemberTest() {
//
//        Member member = createMember();
//        Member saveMember = memberService.saveMember(member);
//
//        assertEquals(member.getName(), saveMember.getName());
//        assertEquals(member.getEmail(), saveMember.getEmail());
//        assertEquals(member.getAddress(), saveMember.getAddress());
//        assertEquals(member.getPassword(), saveMember.getPassword());
//
//    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void saveDuplicateMemberTest() {

        Member member1 = createMember();
        Member member2 = createMember();

        memberService.saveMember(member1);

        Throwable e = assertThrows(IllegalStateException.class, () ->  {
            memberService.saveMember(member2);
        });

        assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }

}
