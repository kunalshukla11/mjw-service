package com.mjw.mjwservice.validation.service;

import com.mjw.mjwservice.user.model.Validatable;
import com.mjw.mjwservice.validation.model.RuleIndex;
import com.mjw.mjwservice.validation.model.ValidationMode;
import com.mjw.mjwservice.validation.model.Violation;
import com.mjw.mjwservice.validation.model.context.ValidationContext;
import com.mjw.mjwservice.validation.validator.RuleValidator;

import java.util.List;

public interface RulesExecutor<T extends Validatable> {

    List<Violation> execute(T model,
                            ValidationMode validationMode,
                            ValidationContext validationContext,
                            RuleValidator.Type type,
                            Class<? extends Validatable> validatingClass);

    RuleIndex getRuleIndex(Validatable payload,
                           Class<? extends Validatable> validatingClass,
                           ValidationMode validationMode);



}
