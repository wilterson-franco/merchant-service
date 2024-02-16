package com.wilterson.cms.assertJ;

import com.wilterson.cms.common.validation.SemanticException;
import org.assertj.core.api.AbstractAssert;

public class SemanticExceptionAssert extends AbstractAssert<SemanticExceptionAssert, SemanticException> {

    public SemanticExceptionAssert(SemanticException e) {
        super(e, SemanticExceptionAssert.class);
    }

    public static SemanticExceptionAssert assertThat(SemanticException actual) {
        return new SemanticExceptionAssert(actual);
    }

    public SemanticExceptionAssert hasSize(int size) {
        isNotNull();
        if (actual.getIssues().size() != size) {
            failWithMessage("Expected size to be %d but was %d", size, actual.getIssues().size());
        }
        return this;
    }

    public SemanticExceptionAssert containsReasonCode(String reasonCode) {
        isNotNull();
        if (actual.getIssues().stream().noneMatch(issue -> issue.getReasonCode().equals(reasonCode))) {
            failWithMessage("Expected to contain an issue with reasonCode %s");
        }
        return this;
    }

    public SemanticExceptionAssert containsSource(String source) {
        isNotNull();
        if (actual.getIssues().stream().noneMatch(issue -> issue.getSource().equals(source))) {
            failWithMessage("Expected to contain an issue with source %s", source);
        }
        return this;
    }

    public SemanticExceptionAssert containsDescription(String description) {
        isNotNull();
        if (actual.getIssues().stream().noneMatch(issue -> issue.getDescription().equals(description))) {
            failWithMessage("Expected to contain an issue with description %s", description);
        }
        return this;
    }
}
