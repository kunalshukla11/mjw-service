package com.mjw.mjwservice.validation.model;

import com.mjw.mjwservice.validation.FieldViolation;
import lombok.Builder;

import java.util.List;

@Builder
public record ValidationResponse(List<FieldViolation> fieldViolations) {

}
