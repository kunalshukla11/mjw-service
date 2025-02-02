package com.mjw.mjwservice.validation.util;

import com.mjw.mjwservice.validation.FieldViolation;
import com.mjw.mjwservice.validation.model.ValidationResponse;
import com.mjw.mjwservice.validation.model.Violation;
import lombok.experimental.UtilityClass;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;

@UtilityClass
public class ValidatorUtil {


    public static ValidationResponse mapToValidationResponse(final List<Violation> violations) {
        final Map<String, List<Violation>> fieldToViolationsMap = violations.stream()
                .collect(groupingBy(violation -> Optional.ofNullable(violation.field())
                        .orElse("Field_Not_Applicable")));
        final List<FieldViolation> fieldViolations = fieldToViolationsMap.entrySet().stream()
                .map(entry -> FieldViolation.builder().field(entry.getKey()).violations(entry.getValue()).build())
                .toList();
        return ValidationResponse.builder().fieldViolations(fieldViolations).build();

    }

    public static List<FieldViolation> mapFieldErrorsToFieldViolations(final List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(fieldError -> FieldViolation.builder()
                        .field(fieldError.getField())
                        .violations(List.of(Violation.builder()
                                .violationType(Violation.ViolationType.ERROR)
                                .message(fieldError.getDefaultMessage())
                                .build()))
                        .build())
                .toList();

    }

}
