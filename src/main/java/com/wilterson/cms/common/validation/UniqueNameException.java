package com.wilterson.cms.common.validation;

public class UniqueNameException extends SemanticValidationException {

    public UniqueNameException() {
        super();
    }

    public UniqueNameException(String errorMessage) {
        super(errorMessage);
    }

    public UniqueNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public UniqueNameException(Throwable cause) {
        super(cause);
    }
}
