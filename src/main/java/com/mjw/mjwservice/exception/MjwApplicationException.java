package com.mjw.mjwservice.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.sf.saxon.expr.Component;

@Data
@EqualsAndHashCode(callSuper = false)
public class MjwApplicationException extends RuntimeException{

    public MjwApplicationException(final String message) {
        super(message);
    }

    public MjwApplicationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
