package com.gamventory.entity;

import com.gamventory.dto.MemberUpdateFormDto;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity {
  
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    
    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {

        String password = passwordEncoder.encode(memberFormDto.getPassword());

        Member member = Member.builder()
                .name(memberFormDto.getName())
                .email(memberFormDto.getEmail())
                .address(memberFormDto.getAddress())
                .password(password)
                .role(Role.USER)
                .build(); 


        return member;
    }

    public void modifyMember(MemberUpdateFormDto memberUpdateForm) {

        this.name = memberUpdateForm.getName();
        this.address = memberUpdateForm.getAddress();
    }

}
    