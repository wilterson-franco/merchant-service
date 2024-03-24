/*
 * Copyright 2024 Wilterson Franco
 */

package com.wilterson.cms.application.port.in;

import static com.wilterson.cms.common.validation.constraint.DefaultLocationsQuantity.SINGLE;

import com.wilterson.cms.common.validation.constraint.DefaultRequired;
import com.wilterson.cms.common.validation.constraint.Unique;
import com.wilterson.cms.common.validation.constraint.UniqueField;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record MultiMerchantCommand(
        @NotBlank
        @Unique(value = UniqueField.NAME, message = "{merchant.name.unique}")
        String name,
        @DefaultRequired(SINGLE)
        @Unique(value = UniqueField.LOCATION, message = "{merchant.location.unique}")
        List<@Valid LocationCommand> locationCommands) {

    public MultiMerchantCommand(
            @NotBlank
            @Unique(value = UniqueField.NAME, message = "{merchant.name.unique}")
            String name,
            @DefaultRequired(SINGLE)
            @Unique(value = UniqueField.LOCATION, message = "{merchant.location.unique}")
            List<@Valid LocationCommand> locationCommands) {

        this.name = name;
        this.locationCommands = locationCommands;
    }
}
