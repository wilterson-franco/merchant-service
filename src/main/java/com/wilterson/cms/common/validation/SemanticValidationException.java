package com.wilterson.cms.common.validation;

public abstract class SemanticValidationException extends RuntimeException {

    public SemanticValidationException() {
        super();
    }

    public SemanticValidationException(String errorMessage) {
        super(errorMessage);
    }

    public SemanticValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SemanticValidationException(Throwable cause) {
        super(cause);
    }
}
