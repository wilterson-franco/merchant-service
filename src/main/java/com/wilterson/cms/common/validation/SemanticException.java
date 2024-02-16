package com.wilterson.cms.common.validation;

import java.util.Collection;
import lombok.Getter;

@Getter
public class SemanticException extends RuntimeException {

    private Collection<? extends Issue> issues;

    public SemanticException(Collection<? extends Issue> issues) {
        this.issues = issues;
    }
}
