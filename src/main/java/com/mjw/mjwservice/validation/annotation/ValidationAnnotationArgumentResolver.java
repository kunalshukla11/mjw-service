package com.mjw.mjwservice.validation.annotation;

import com.mjw.mjwservice.user.model.Validatable;
import com.mjw.mjwservice.validation.model.context.DefaultValidationContext;
import com.mjw.mjwservice.validation.service.ValidationOrchestrator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
@Component
public class ValidationAnnotationArgumentResolver implements HandlerMethodArgumentResolver  {

    private final ValidationOrchestrator validationOrchestrator;
    private final List<HttpMessageConverter<?>> messageConverters;

    @Override
    public boolean supportsParameter(@NotNull final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Validator.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter,
                                  final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest,
                                  final WebDataBinderFactory binderFactory) throws Exception {
        final HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        final String requestorSid = Optional.ofNullable(httpServletRequest)
                .map(request -> request.getHeader("X-Requestor-Sid"))
                .filter(StringUtils::isNotEmpty)
                .orElseGet(() -> {
                    log.warn("X-Requestor-Sid header not found, defaulting to anonymous user");
                    return "anonymous";
                });

        final Object body = readWithMessageConverters(httpServletRequest, parameter.getParameterType());

        return validateRequestPayload(body, parameter, requestorSid);
    }

    private Object validateRequestPayload(final Object body, final MethodParameter parameter,
                                          final String requestorSid) {
        final Class<? extends Validatable> validatingClass = Optional.of(parameter)
                .filter(mp -> mp.hasParameterAnnotation(Validator.class))
                .map(mp -> mp.getParameterAnnotation(Validator.class))
                .map(Validator::validatingClass)
                .orElseThrow(() ->
                        new RuntimeException("Rules Executor used with Validator Annotation does not exist"));

        final DefaultValidationContext defaultValidationContext =
                DefaultValidationContext.builder().requestorSid(requestorSid).build();
        return validationOrchestrator.orchestrate((Validatable) body, validatingClass, defaultValidationContext);
    }


    @SuppressWarnings("unchecked")
    public <T> T readWithMessageConverters(final HttpServletRequest request,
                                           final Class<T> targetType) throws IOException {
        final HttpInputMessage inputMessage = new ServletServerHttpRequest(request);

        for (HttpMessageConverter<?> converter : messageConverters) {
            if (converter.canRead(targetType, null)) {
                // Found a suitable converter, use it to read the request body
                final HttpMessageConverter<T> specificConverter = (HttpMessageConverter<T>) converter;
                return specificConverter.read(targetType, inputMessage);
            }
        }

        throw new IllegalArgumentException("No suitable message converter found for type: " + targetType.getName());
    }

}
