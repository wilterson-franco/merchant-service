package com.wilterson.cms.common.validation.syntatic;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
public class SyntacticValidator {

    private final Validator validator;// = buildDefaultValidatorFactory().getValidator();

    public <T> void validate(T subject) {
        Set<ConstraintViolation<T>> violations = validator.validate(subject);
        if (!CollectionUtils.isEmpty(violations)) {
            throw new ConstraintViolationException(violations);
        }
    }
}
