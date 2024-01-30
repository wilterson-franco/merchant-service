package com.wilterson.cms.application.port.in;

import com.wilterson.cms.application.domain.model.Location;
import com.wilterson.cms.application.domain.model.MerchantType;
import com.wilterson.cms.common.validation.SyntacticValidator;
import com.wilterson.cms.common.validation.Validatable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;

public record MerchantCommand(
        @NotBlank(message = "{merchant.name.required}") String name,
        @NotNull(message = "Type can't be null") MerchantType type,
        Collection<Location> locations) implements Validatable {

    public MerchantCommand(String name, MerchantType type, Collection<Location> locations) {

        this.name = name;
        this.type = type;
        this.locations = locations;

        // syntactic validation (input fields)
        SyntacticValidator.validate(this);
    }
}
