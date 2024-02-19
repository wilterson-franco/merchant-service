package com.wilterson.cms.application.port.in;

import jakarta.validation.constraints.NotBlank;

public record LocationCommand(

        @NotBlank(message = "countryCode can't be empty")
        String countryCode,
        boolean isDefault
) {

}
