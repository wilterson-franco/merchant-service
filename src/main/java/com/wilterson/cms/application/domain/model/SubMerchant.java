/*
 * Copyright 2024 Wilterson Franco
 */

package com.wilterson.cms.application.domain.model;

import java.util.List;
import lombok.Data;

@Data
public class SubMerchant {

    private final String guid;
    private final String name;
    private final List<Location> locations;

    private SubMerchant(SubMerchantBuilder builder) {
        this.name = builder.name;
        this.guid = builder.guid;
        this.locations = builder.locations;
    }

    public static class SubMerchantBuilder {

        // mandatory parameters
        private final String name;
        private final String guid;

        // optional parameters
        private List<Location> locations;

        public SubMerchantBuilder(final String name, final String guid) {
            this.name = name;
            this.guid = guid;
        }

        public SubMerchantBuilder locations(final List<Location> locations) {
            this.locations = locations;
            return this;
        }

        public SubMerchant build() {
            return new SubMerchant(this);
        }
    }
}
