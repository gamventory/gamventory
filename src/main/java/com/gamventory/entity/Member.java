package com.gamventory.entity;

import com.gamventory.dto.MemberPasswordDto;
import com.gamventory.dto.MemberUpdateFormDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gamventory.constant.Role;
import com.gamventory.dto.MemberFormDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "member")
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity {

    // 회원마다 존재하는 회원번호 -> 자동증가
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이름
    private String name;

    // 이메일 -> 고유값
    @Column(unique = true)
    private String email;

    // 비밀번호
    private String password;

    // 우편 번호
    private String zipcode;

    // 지번 주소
    private String streetAddress;

    // 상세 주소
    private String detailAddress;

    // 계정 등급
    @Enumerated(EnumType.STRING)
    private Role role;

    // 소셜 로그인 제공자
    private String socialProvider;

    // 소셜 로그인 제공자의 사용자 ID
    private String socialId;

    // 회원 가입 메소드(MemberFormDto, PasswordEncoder)
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {

        String password = passwordEncoder.encode(memberFormDto.getPassword());

        Member member = Member.builder()
                .name(memberFormDto.getName())
                .email(memberFormDto.getEmail())
                .password(password)
                .zipcode(memberFormDto.getZipCode())
                .streetAddress(memberFormDto.getStreetAddress())
                .detailAddress(memberFormDto.getDetailAddress())
                .role(Role.USER)
                .build();
        return member;
    }

    public void modifyMember(MemberUpdateFormDto memberUpdateForm) {

        this.name = memberUpdateForm.getName();
        this.zipcode = memberUpdateForm.getZipCode();
        this.streetAddress = memberUpdateForm.getStreetAddress();
        this.detailAddress = memberUpdateForm.getDetailAddress();
    }

    public void modifyMemberPassword(MemberPasswordDto memberPasswordDto, PasswordEncoder passwordEncoder) {

        String newPassword = passwordEncoder.encode(memberPasswordDto.getNewPassword());
        this.password = newPassword;
    }

    public static Member createSocialMember(String name, String email, String socialProvider, String socialId,
                                            PasswordEncoder passwordEncoder) {

        String dummyPassword = passwordEncoder.encode("dummy_password_" + System.currentTimeMillis());

        return Member.builder()
                .name(name)
                .email(email)
                .password(dummyPassword)
                .socialProvider(socialProvider)
                .socialId(socialId)
                .role(Role.USER)
                .build();
    }

}
    