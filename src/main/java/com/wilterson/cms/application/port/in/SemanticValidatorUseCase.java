package com.wilterson.cms.application.port.in;

import com.wilterson.cms.common.validation.Validatable;

@FunctionalInterface
public interface SemanticValidatorUseCase<T extends Validatable> {

    void validate(T model);

    default SemanticValidatorUseCase<T> andThen(SemanticValidatorUseCase<? super T> after) {
        return (T t) -> {
            validate(t);
            after.validate(t);
        };
    }
}
