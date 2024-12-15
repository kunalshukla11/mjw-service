package com.mjw.mjwservice.validation.model.context;

import lombok.Builder;
import lombok.With;

@Builder
public record DefaultValidationContext(@With Object payload) implements ValidationContext {

}
