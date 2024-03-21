package com.wilterson.cms.common.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Component
public class SyntacticValidator {

    private final LocalValidatorFactoryBean validator;

    public SyntacticValidator(LocalValidatorFactoryBean validator) {
        this.validator = validator;
    }

    public <T> void validate(T subject) {

        Set<ConstraintViolation<T>> violations = validator.validate(subject);
        if (!CollectionUtils.isEmpty(violations)) {
            throw new ConstraintViolationException(violations);
        }
    }
}
