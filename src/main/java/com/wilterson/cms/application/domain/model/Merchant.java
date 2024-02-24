package com.wilterson.cms.application.domain.model;

import com.wilterson.cms.common.validation.semantic.Validatable;
import com.wilterson.cms.common.validation.syntatic.SyntacticValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Data;

@Data
public class Merchant implements Validatable {

    @NotBlank
    private final String guid;
    @NotBlank
    private final String name;
    @NotNull
    private final MerchantType type;
    private final Set<Location> locations;

    private Merchant(MerchantBuilder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.guid = builder.guid;
        this.locations = builder.locations;

        // syntactic validations (input validations)
        SyntacticValidator.validate(this);
    }

    public static class MerchantBuilder {

        // mandatory parameters
        private final String name;
        private final String guid;
        private final MerchantType type;

        // optional parameters
        private Set<Location> locations;

        public MerchantBuilder(String name, String guid, MerchantType type) {
            this.name = name;
            this.guid = guid;
            this.type = type;
        }

        public MerchantBuilder locations(Set<Location> locations) {
            this.locations = locations;
            return this;
        }

        public Merchant build() {
            return new Merchant(this);
        }
    }
}
