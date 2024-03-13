/*
 * Copyright 2024 Wilterson Franco
 */
package com.wilterson.cms.common.validation.constraint;

import com.wilterson.cms.application.domain.model.Location;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class DefaultLocationRequiredValidator implements ConstraintValidator<DefaultRequired, List<Location>> {

    private DefaultLocationsQuantity quantity;

    @Override
    public void initialize(DefaultRequired constraintAnnotation) {
        quantity = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(List<Location> locations, ConstraintValidatorContext constraintContext) {

        if (CollectionUtils.isEmpty(locations)) {
            return true;
        }

        return locations.stream().anyMatch(Location::isDefault);
    }
}
