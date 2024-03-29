/*
 * Copyright 2024 Wilterson Franco
 */

package com.wilterson.cms.application.domain.model;

import java.util.List;

public class SubMerchant extends Merchant {

    private final List<Location> locations;

    private SubMerchant(SubMerchantBuilder builder) {
        super(builder.getName(), builder.getGuid());
        this.locations = builder.locations;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public static class SubMerchantBuilder extends MerchantBuilder<SubMerchant> {

        // optional parameters
        private List<Location> locations;

        // mandatory parameters enforced through the constructor
        public SubMerchantBuilder(final String name, final String guid) {
            super(name, guid);
        }

        public SubMerchantBuilder locations(final List<Location> locations) {
            this.locations = locations;
            return this;
        }

        @Override
        public SubMerchant build() {
            return new SubMerchant(this);
        }
    }
}
