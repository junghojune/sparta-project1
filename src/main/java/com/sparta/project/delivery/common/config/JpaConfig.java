package com.sparta.project.delivery.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;


@EnableJpaAuditing
@Configuration
public class JpaConfig {
    // Auditing 할 때 생성자 이름을 넣어주기 위한 필드 설정
    // // TODO : Security 가 정의되면 적용되면 변경하기
//    @Bean
//    public AuditorAware<String> auditorAware() {
//        return () -> Optional.of("temporary");
//    }
}

