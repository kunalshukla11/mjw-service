package com.mjw.mjwservice.validation.annotation;

import com.mjw.mjwservice.user.model.Validatable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Validator {

    Class<? extends Validatable> validatingClass();

}
