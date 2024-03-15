/*
 * Copyright 2024 Wilterson Franco
 */

package com.wilterson.cms.common.validation.constraint;

import com.wilterson.cms.common.cache.CacheManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueStringValidator implements ConstraintValidator<Unique, String> {

    private final CacheManager cacheManager;
    private UniqueField uniqueField;

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.uniqueField = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintValidatorContext) {

        if (object == null) {
            return true;
        }
        if (uniqueField == UniqueField.NAME) {
            return cacheManager.getEntityByName(object).isEmpty();
        }
        return cacheManager.getEntityByGuid(object).isEmpty();
    }
}
