package com.mjw.mjwservice.validation;

import com.mjw.mjwservice.validation.model.Violation;
import lombok.Builder;

import java.util.List;

@Builder
public record FieldViolation(String field, List<Violation> violations) {

}
