/*
 * Copyright 2024 Wilterson Franco
 */
package com.wilterson.cms.assertJ;

import com.wilterson.cms.common.validation.Issue;
import org.assertj.core.api.AbstractAssert;

public class IssueAssert extends AbstractAssert<IssueAssert, Issue> {

    public IssueAssert(Issue issue) {
        super(issue, IssueAssert.class);
    }

    public static IssueAssert assertThat(Issue actual) {
        return new IssueAssert(actual);
    }

    public IssueAssert isRecoverable() {
        isNotNull();
        if (Boolean.FALSE.equals(actual.isRecoverable())) {
            failWithMessage("Expected exception recoverable to be true but was false");
        }
        return this;
    }

    public IssueAssert isNotRecoverable() {
        isNotNull();
        if (Boolean.TRUE.equals(actual.isRecoverable())) {
            failWithMessage("Expected exception recoverable to be false but was true");
        }
        return this;
    }

    public IssueAssert hasDescription(String description) {
        isNotNull();
        if (!actual.getDescription().equals(description)) {
            failWithMessage("Expected to have description %s but was %s", description, actual.getDescription());
        }
        return this;
    }
}
