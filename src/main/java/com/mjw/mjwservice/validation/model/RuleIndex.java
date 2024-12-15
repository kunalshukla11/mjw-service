package com.mjw.mjwservice.validation.model;

import lombok.Builder;

import java.util.Set;

@Builder
public record RuleIndex(ValidationMode validationMode, Set<String> validators) {

}
