package com.wilterson.cms.common.validation;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import org.springframework.util.CollectionUtils;

public class SyntacticValidator {

    private final static Validator validator = buildDefaultValidatorFactory().getValidator();

    public static <T> void validate(T subject) {
        Set<ConstraintViolation<T>> violations = validator.validate(subject);
        if (!CollectionUtils.isEmpty(violations)) {
            throw new ConstraintViolationException(violations);
        }
    }
}
