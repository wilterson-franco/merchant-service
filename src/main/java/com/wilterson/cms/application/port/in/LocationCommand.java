/*
 * Copyright 2024 Wilterson Franco
 */
package com.wilterson.cms.application.port.in;

import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

public record LocationCommand(

        @NotBlank(message = "countryCode can't be empty")
        String countryCode,
        boolean isDefault
) implements Comparable<LocationCommand> {

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocationCommand location)) {
            return false;
        }
        return Objects.equals(countryCode, location.countryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryCode);
    }

    @Override
    public int compareTo(LocationCommand other) {
        return Objects.equals(this.countryCode, other.countryCode) ? 0 : -1;
    }
}
