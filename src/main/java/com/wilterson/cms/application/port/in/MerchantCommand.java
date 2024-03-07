package com.wilterson.cms.application.port.in;

import static com.wilterson.cms.common.validation.constraint.DefaultLocationsQuantity.SINGLE;

import com.wilterson.cms.common.validation.constraint.DefaultRequired;
import com.wilterson.cms.common.validation.constraint.Unique;
import com.wilterson.cms.common.validation.constraint.UniqueField;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record MerchantCommand(
        @NotBlank
        @Unique(value = UniqueField.NAME, message = "{merchant.name.unique}")
        String name,
        @NotNull(message = "Type can't be null")
        MerchantTypeCommand type,
        @DefaultRequired(SINGLE)
        @Unique(value = UniqueField.LOCATION, message = "{merchant.location.unique}")
        List<@Valid LocationCommand> locationCommands) {

    public MerchantCommand(
            String name,
            MerchantTypeCommand type,
            List<LocationCommand> locationCommands) {

        this.name = name;
        this.type = type;
        this.locationCommands = locationCommands;
    }
}
