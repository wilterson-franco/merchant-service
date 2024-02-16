package com.wilterson.cms.application.domain.model;

import com.wilterson.cms.common.validation.SyntacticValidator;
import com.wilterson.cms.common.validation.Validatable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Location implements Validatable {

    @NotBlank
    private String countryCode;
    private boolean isDefault;

    private Location(Location.LocationBuilder builder) {
        this.countryCode = builder.countryCode;
        this.isDefault = builder.isDefault;

        // syntactic validations (input validations)
        SyntacticValidator.validate(this);
    }

    public static class LocationBuilder {

        // mandatory parameters
        private String countryCode;

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
}
