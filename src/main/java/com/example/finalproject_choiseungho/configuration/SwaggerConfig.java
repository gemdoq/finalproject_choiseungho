package com.example.finalproject_choiseungho.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {
    private String TITLE = "스프링부트와 JPA를 활용한 MutsaSNS API with Swagger";
    private String DESC = "<h3>회원가입, 로그인, 인증 및 인가, 게시글 및 댓글의 CRUD, 마이피드, 좋아요, 알람</h3><img src=\"https://user-images.githubusercontent.com/85062826/211473810-0030336e-b459-467f-9318-314e7c613965.png\" width=300 height=300 />";
    private String NAME = "최승호";
    private String URL = "https://gemdoq.github.io";
    private String EMAIL = "gemdoq@gmail.com";
    private String VERSION = "1.0.0";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .ignoredParameterTypes(Authentication.class, Pageable.class)
                .securitySchemes(Arrays.asList(HttpAuthenticationScheme.JWT_BEARER_BUILDER
                        .name("JWT")
                        .description("토큰 타입(Bearer)을 제외한 토큰 값(JWT)만 입력해주세요.")
                        .build()))
                .securityContexts(Arrays.asList(securityContext()))
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.finalproject_choiseungho"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(TITLE)
                .description(DESC)
                .contact(new Contact(NAME, URL, EMAIL))
                .version(VERSION)
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        return Arrays.asList(SecurityReference.builder().scopes(new AuthorizationScope[0]).reference("JWT").build());
    }
}