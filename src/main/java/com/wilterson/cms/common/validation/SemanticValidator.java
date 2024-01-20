package com.wilterson.cms.common.validation;

public interface SemanticValidator<T extends Validatable> {

    void validate(T model);
}
