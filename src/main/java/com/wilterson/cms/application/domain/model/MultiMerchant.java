/*
 * Copyright 2024 Wilterson Franco
 */

package com.wilterson.cms.application.domain.model;

import java.util.List;

public class MultiMerchant extends Merchant {

    private final List<Location> locations;

    private MultiMerchant(MultiMerchantBuilder builder) {
        super(builder.getName(), builder.getGuid());
        this.locations = builder.locations;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public static class MultiMerchantBuilder extends MerchantBuilder<MultiMerchant> {

        // optional parameters
        private List<Location> locations;

        // mandatory parameters enforced through the constructor
        public MultiMerchantBuilder(final String name, final String guid) {
            super(name, guid);
        }

        public MultiMerchantBuilder locations(final List<Location> locations) {
            this.locations = locations;
            return this;
        }

        @Override
        public MultiMerchant build() {
            return new MultiMerchant(this);
        }
    }
}
