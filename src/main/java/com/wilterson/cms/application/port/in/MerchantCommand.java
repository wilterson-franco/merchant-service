package com.wilterson.cms.application.port.in;

import com.wilterson.cms.application.domain.model.MerchantType;
import com.wilterson.cms.common.validation.SyntacticValidator;
import com.wilterson.cms.common.validation.Validatable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record MerchantCommand(
        @NotBlank(message = "{merchant.name.required}") String name,
        @NotNull(message = "Type can't be null") MerchantType type,
        Set<LocationCommand> locationCommands) implements Validatable {

    public MerchantCommand(String name, MerchantType type, Set<LocationCommand> locationCommands) {

        this.name = name;
        this.type = type;
        this.locationCommands = locationCommands;

        // syntactic validation (input fields)
        SyntacticValidator.validate(this);
    }
}
