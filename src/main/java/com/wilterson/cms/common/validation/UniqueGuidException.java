package com.wilterson.cms.common.validation;

public class UniqueGuidException extends SemanticValidationException {

    public UniqueGuidException() {
        super();
    }

    public UniqueGuidException(String errorMessage) {
        super(errorMessage);
    }

    public UniqueGuidException(String message, Throwable cause) {
        super(message, cause);
    }

    public UniqueGuidException(Throwable cause) {
        super(cause);
    }
}
