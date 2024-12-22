package com.mjw.mjwservice.validation;

import com.mjw.mjwservice.validation.annotation.ValidationAnnotationArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@RequiredArgsConstructor
@Configuration
public class ValidationArgumentResolverConfig implements WebMvcConfigurer {


    private final ValidationAnnotationArgumentResolver validationAnnotationArgumentResolver;

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(validationAnnotationArgumentResolver);
    }


}
