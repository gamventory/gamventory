// package com.gamventory.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.domain.AuditorAware;
// import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// @Configuration
// @EnableJpaAuditing //JPA의 Auditing 기능을 활성화하여 엔티티의 생성 및 수정, 수정자 정보를 자동으로 추적,관리하는 어노테이션
// public class AuditConfig {
    
//     @Bean 
//     public AuditorAware<String> auditorProvider(){
//         return new AuditorAwareImpl();
//     }
// }
