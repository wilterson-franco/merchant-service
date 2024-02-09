package com.wilterson.cms.common.validation;

@FunctionalInterface
public interface SemanticValidator<T extends Validatable> {

    void validate(T model);

    default SemanticValidator<T> andThen(SemanticValidator<? super T> after) {
        return (T t) -> {
            validate(t);
            after.validate(t);
        };
    }
}
