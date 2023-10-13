// package com.gamventory.config;

// import java.util.Optional;

// import org.springframework.data.domain.AuditorAware;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;

// public class AuditorAwareImpl implements AuditorAware<String>{

//     /*
//      * JPA를 이용해서 엔티티가 생성되거나 수정될 때 현재 접속한 사용자의 정보를 제공할 수 있습니다.
//      * @CreatedBy나 @LastModifiedBy 필드에 현재 접속한 사용자의 이름을 자동으로 설정할 수 있습니다.
//      */

    
//     @Override
//     public Optional<String> getCurrentAuditor() {
//         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//         String userId = "";
//         if(authentication != null){
//             userId = authentication.getName();
//         }
//         return Optional.of(userId);
//     }
    
// }
    