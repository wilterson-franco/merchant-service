package com.wilterson.cms.common.validation.constraint;

import com.wilterson.cms.application.domain.model.Location;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueLocationValidator implements ConstraintValidator<Unique, List<Location>> {

    @Override
    public boolean isValid(List<Location> locations, ConstraintValidatorContext constraintValidatorContext) {

        if (locations == null) {
            return true;
        }

        Set<Location> uniqueLocations = new HashSet<>();

        return locations.stream().allMatch(uniqueLocations::add);
    }
}
