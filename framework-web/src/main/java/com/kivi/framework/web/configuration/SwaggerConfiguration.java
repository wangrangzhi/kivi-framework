package com.kivi.framework.web.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.kivi.framework.constant.Constant;
import com.kivi.framework.properties.KtfProperties;
import com.kivi.framework.util.kit.CollectionKit;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(
                        prefix = "framework.swagger",
                        name = "enabled",
                        havingValue = "true",
                        matchIfMissing = false )
public class SwaggerConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private KtfProperties ktfProperties;

    @Override
    public void addResourceHandlers( ResourceHandlerRegistry registry ) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

    }

    /**
     * 可以定义多个组，比如本类中定义把test和demo区分开了 （访问页面就可以看到效果了）
     *
     */
    @ConditionalOnProperty(
                            prefix = "framework.swagger",
                            name = "authorization-enabled",
                            havingValue = "true",
                            matchIfMissing = false )
    @Bean
    public Docket apiWithAuth() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();

        tokenPar.name(Constant.HTTP_AUTHORIZATION).description("认证令牌，字段名：" + Constant.HTTP_AUTHORIZATION)
                .modelRef(new ModelRef("string")).parameterType("header").required(false).build();

        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                // .groupName("后台接口") // 不理解加上以后/v2/api-docs访问不到
                .consumes(
                        CollectionKit.newHashSet(MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE))
                .genericModelSubstitutes(DeferredResult.class).genericModelSubstitutes(ResponseEntity.class)
                .globalOperationParameters(pars).useDefaultResponseMessages(false).securitySchemes(securitySchemes())
                .securityContexts(securityContexts()).forCodeGeneration(true).enableUrlTemplating(true).pathMapping("/")// base，最终调用接口后会和paths拼接在一
                .apiInfo(buildApiInfo()).select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any()).build();
    }

    @ConditionalOnProperty(
                            prefix = "framework.swagger",
                            name = "authorization-enabled",
                            havingValue = "false",
                            matchIfMissing = false )
    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                // .groupName("后台接口") // 不理解加上以后/v2/api-docs访问不到
                .consumes(
                        CollectionKit.newHashSet(MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE))
                .genericModelSubstitutes(DeferredResult.class).genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false).forCodeGeneration(true).enableUrlTemplating(true).pathMapping("/")// base，最终调用接口后会和paths拼接在一
                .apiInfo(buildApiInfo()).select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder().title(ktfProperties.getSwagger().getTitleUTF8())// 大标题
                .description(ktfProperties.getSwagger().getDescriptionUTF8())// 详细描述
                .version(ktfProperties.getSwagger().getVersion())// 版本
                .termsOfServiceUrl(ktfProperties.getSwagger().getTermsOfServiceUrl())
                .license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html").build();
    }

    private List<ApiKey> securitySchemes() {
        return CollectionKit.newArrayList(new ApiKey("mykey", "api_key", "header"));
    }

    private List<SecurityContext> securityContexts() {

        return CollectionKit.newArrayList(SecurityContext.builder().securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/api/*")).build());
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return CollectionKit.newArrayList(new SecurityReference("mykey", authorizationScopes));
    }

    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration("test-app-client-id", "test-app-client-secret", "test-app-realm", "test-app",
                "mykey", ApiKeyVehicle.HEADER, "api_key",
                "," /* scope separator */);
    }

    @Bean
    UiConfiguration uiConfig() {
        return new UiConfiguration("validatorUrl", // url
                "none", // docExpansion => none | list
                "alpha", // apiSorter => alpha
                "schema", // defaultModelRendering => schema
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, true, // enableJsonEditor
                                                                        // =>
                                                                        // true
                                                                        // |
                                                                        // false
                true, // showRequestHeaders => true | false
                120000L); // requestTimeout => in milliseconds, defaults to null
                          // (uses jquery xh timeout)
    }

}
