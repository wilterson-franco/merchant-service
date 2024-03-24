/*
 * Copyright 2024 Wilterson Franco
 */

package com.wilterson.cms.application.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Merchant {

    private final String guid;
    private final String name;

    public Merchant(String name, String guid) {
        this.name = name;
        this.guid = guid;
    }

    @Getter
    public abstract static class MerchantBuilder<T extends Merchant> {

        // mandatory parameters
        private final String name;
        private final String guid;

        // optional parameters
        // ...

        public MerchantBuilder(final String name, final String guid) {
            this.name = name;
            this.guid = guid;
        }

        public abstract T build();
    }
}
