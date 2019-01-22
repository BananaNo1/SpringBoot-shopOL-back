package com.wust.graproject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName SwaggerConfig
 * @Description TODO
 * @Author leis
 * @Date 2019/1/22 18:18
 * @Version 1.0
 **/

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Value("${swagger2.host}")
    private String host;

    @Bean
    public Docket userManager() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("用户操作")
                .host(host)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wust.graproject.controller"))
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("商城-API")
                .description("http://www.51wustzds.com/")
                .version("1.0")
                .termsOfServiceUrl("http://www.51wustzds.com/")
                .contact(new Contact("在线买卖", "http://www.51wustzds.com/", "893052525@qq.com"))
                .build();
    }
}
