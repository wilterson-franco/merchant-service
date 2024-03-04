package com.wilterson.cms.common.validation.constraint;

import com.wilterson.cms.application.port.in.LocationCommand;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueLocationCommandValidator implements ConstraintValidator<Unique, List<LocationCommand>> {

    @Override
    public boolean isValid(List<LocationCommand> locations, ConstraintValidatorContext constraintValidatorContext) {

        if (locations == null) {
            return true;
        }

        Set<LocationCommand> uniqueLocations = new HashSet<>();

        return locations.stream().allMatch(uniqueLocations::add);
    }
}
