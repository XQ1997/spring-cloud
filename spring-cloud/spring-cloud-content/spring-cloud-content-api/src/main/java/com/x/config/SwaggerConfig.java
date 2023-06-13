package com.x.config;

import com.x.base.api.ResultCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author XuQiang
 * @ClassName SwaggerConfig
 * @Description swagger接口文档配置类
 * @date 2021/10/8 16:23
 * @Version 1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    Docket createRestApi(){
        //添加全局响应状态码
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        Arrays.stream(ResultCode.values()).forEach(resultCode -> {
            responseMessageList.add(
                    new ResponseMessageBuilder().code(Integer.parseInt(resultCode.getCode())).message(resultCode.getMessage()).responseModel(
                            new ModelRef(resultCode.getMessage())).build()
            );
        });
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            // 这里采用只扫描具有@RestController注解的controller https://www.cnblogs.com/acm-bingzi/p/swagger2-controller.html
//            .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
            .apis(RequestHandlerSelectors.basePackage("com.x.content"))
            .paths(PathSelectors.any())
            .build()
            // 添加全局响应状态码
            .globalResponseMessage(RequestMethod.GET, responseMessageList)
            .globalResponseMessage(RequestMethod.POST, responseMessageList)
            .globalResponseMessage(RequestMethod.PUT, responseMessageList)
            .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
            /*.globalOperationParameters(pars)*/;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("学成在线内容管理系统")
                .description("内容系统管理系统对课程相关信息进行业务管理数据")
                .version("1.0")
                .contact(new Contact("XuQiang",
                        "http://www.qq.com",
                        "2645564458@qq.com"))
                .build();
    }
}