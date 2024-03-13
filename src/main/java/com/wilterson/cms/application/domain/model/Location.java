/*
 * Copyright 2024 Wilterson Franco
 */
package com.wilterson.cms.application.domain.model;

import jakarta.validation.constraints.NotBlank;
import java.util.Objects;
import lombok.Data;

@Data
public class Location implements Comparable<Location> {

    @NotBlank
    private String countryCode;
    private boolean isDefault;

    private Location(Location.LocationBuilder builder) {
        this.countryCode = builder.countryCode;
        this.isDefault = builder.isDefault;
    }

    public static class LocationBuilder {

        // mandatory parameters
        private final String countryCode;

        // optional parameters
        private boolean isDefault;

        public LocationBuilder(String countryCode) {
            this.countryCode = countryCode;
        }

        public LocationBuilder defaultLocation(boolean isDefault) {
            this.isDefault = isDefault;
            return this;
        }

        public Location build() {
            return new Location(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location location)) {
            return false;
        }
        return Objects.equals(countryCode, location.countryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryCode);
    }

    @Override
    public int compareTo(Location other) {
        return Objects.equals(this.getCountryCode(), other.getCountryCode()) ? 0 : -1;
    }
}
