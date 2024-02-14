package com.wilterson.cms.common.validation;

public class LocationValidationException extends SemanticValidationException {

    public LocationValidationException() {
        super();
    }

    public LocationValidationException(String errorMessage) {
        super(errorMessage);
    }

    public LocationValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public LocationValidationException(Throwable cause) {
        super(cause);
    }
}
