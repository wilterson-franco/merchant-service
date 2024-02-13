package com.wilterson.cms.assertJ;

import com.wilterson.cms.application.domain.service.ApplicationException;
import org.assertj.core.api.AbstractAssert;

public class ApplicationExceptionAssert extends AbstractAssert<ApplicationExceptionAssert, ApplicationException> {

    public ApplicationExceptionAssert(ApplicationException e) {
        super(e, ApplicationExceptionAssert.class);
    }

    public static ApplicationExceptionAssert assertThat(ApplicationException actual) {
        return new ApplicationExceptionAssert(actual);
    }

    public ApplicationExceptionAssert hasReasonCode(String reasonCode) {
        isNotNull();
        if (!actual.getReasonCode().equals(reasonCode)) {
            failWithMessage("Expected exception reasonCode to be %s but was %s", reasonCode, actual.getReasonCode());
        }
        return this;
    }

    public ApplicationExceptionAssert hasSource(String source) {
        isNotNull();
        if (!actual.getSource().equals(source)) {
            failWithMessage("Expected exception source to be %s but was %s", source, actual.getSource());
        }
        return this;
    }

    public ApplicationExceptionAssert hasDescription(String description) {
        isNotNull();
        if (!actual.getDescription().equals(description)) {
            failWithMessage("Expected exception description to be %s but was %s", description, actual.getDescription());
        }
        return this;
    }

    public ApplicationExceptionAssert isRecoverable() {
        isNotNull();
        if (Boolean.FALSE.equals(actual.isRecoverable())) {
            failWithMessage("Expected exception recoverable to be true but was false");
        }
        return this;
    }

    public ApplicationExceptionAssert isNotRecoverable() {
        isNotNull();
        if (Boolean.TRUE.equals(actual.isRecoverable())) {
            failWithMessage("Expected exception recoverable to be false but was true");
        }
        return this;
    }
}
