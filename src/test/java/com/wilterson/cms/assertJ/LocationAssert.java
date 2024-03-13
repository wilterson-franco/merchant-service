/*
 * Copyright 2024 Wilterson Franco
 */
package com.wilterson.cms.assertJ;

import com.wilterson.cms.application.domain.model.Location;
import org.assertj.core.api.AbstractAssert;

public class LocationAssert extends AbstractAssert<LocationAssert, Location> {

    public LocationAssert(Location location) {
        super(location, LocationAssert.class);
    }

    public static LocationAssert assertThat(Location actual) {
        return new LocationAssert(actual);
    }

    public LocationAssert countryCodeEqualsTo(String countryCode) {
        isNotNull();
        if (!actual.getCountryCode().equals(countryCode)) {
            failWithMessage("Expected location to have countryCode %s but was %s.", countryCode, actual.getCountryCode());
        }
        return this;
    }

    public LocationAssert isDefault() {
        isNotNull();
        if (!actual.isDefault()) {
            failWithMessage("Expected location to be default.");
        }
        return this;
    }
}
