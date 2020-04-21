package com.ssj.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * 通过 createRestApi函数来构建一个DocketBean
     * 方法名可以随意命名
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())//调用apiInfo方法,创建一个ApiInfo实例,里面是展示在文档页面信息内容
            .select()
            //控制暴露出去的路径下的实例
            //如果某个接口不想暴露,可以使用以下注解@ApiIgnore
            //这样该接口就不会暴露在swagger2的页面下
            .apis(RequestHandlerSelectors.basePackage("com.ssj.user.controller"))
            .paths(PathSelectors.any())
            .build();
    }

    //构建 api文档的详细信息函数
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            //页面标题
            .title("ssj-user API Swagger")
            //条款地址
            //.termsOfServiceUrl("http://despairyoke.github.io/")
            //.contact("zwd")
            .version("1.0.0")
            //描述
            .description("API 文档")
            .build();
    }

}
