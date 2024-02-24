package com.wilterson.cms.common.validation.semantic;

import com.wilterson.cms.common.validation.Issue;
import java.util.Set;

@FunctionalInterface
public interface SemanticValidator<T extends Validatable> {

    void validate(T model, Set<Issue> issues);

    default SemanticValidator<T> andThen(SemanticValidator<? super T> after) {
        return (T t, Set<Issue> issues) -> {
            validate(t, issues);
            after.validate(t, issues);
        };
    }
}
