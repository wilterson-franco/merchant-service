/*
 * Copyright 2024 Wilterson Franco
 */
package com.wilterson.cms.common.validation.constraint;

import com.wilterson.cms.application.port.in.LocationCommand;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class DefaultLocationCommandRequiredValidator implements ConstraintValidator<DefaultRequired, List<LocationCommand>> {

    private DefaultLocationsQuantity quantity;

    @Override
    public void initialize(DefaultRequired constraintAnnotation) {
        quantity = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(List<LocationCommand> locations, ConstraintValidatorContext constraintContext) {

        if (CollectionUtils.isEmpty(locations)) {
            return true;
        }

        return locations.stream().anyMatch(LocationCommand::isDefault);
    }
}
