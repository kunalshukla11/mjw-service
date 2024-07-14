package com.mjw.mjwservice.validation.model.context;

import com.mjw.mjwservice.user.model.Validatable;
import lombok.Builder;
import lombok.With;

@Builder
public record DefaultValidationContext(@With Object payload) implements ValidationContext<Validatable> {

}
