package com.wilterson.cms.application.domain.model;

import static com.wilterson.cms.common.validation.constraint.DefaultLocationsQuantity.SINGLE;

import com.wilterson.cms.common.validation.constraint.CaseMode;
import com.wilterson.cms.common.validation.constraint.CheckCase;
import com.wilterson.cms.common.validation.constraint.DefaultRequired;
import com.wilterson.cms.common.validation.constraint.Unique;
import com.wilterson.cms.common.validation.constraint.UniqueField;
import com.wilterson.cms.common.validation.semantic.Validatable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
public class Merchant implements Validatable {

    @CheckCase(value = CaseMode.UPPER)
    @NotBlank
    @Unique(value = UniqueField.GUID, message = "{merchant.guid.unique}")
    private final String guid;

    @NotBlank
    @Unique(value = UniqueField.NAME, message = "{merchant.name.unique}")
    private final String name;

    @NotNull
    private final MerchantType type;

    @DefaultRequired(SINGLE)
    @Unique(value = UniqueField.LOCATION, message = "{merchant.location.unique}")
    private final List<Location> locations;

    private Merchant(MerchantBuilder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.guid = builder.guid;
        this.locations = builder.locations;
    }

    public static class MerchantBuilder {

        // mandatory parameters
        private final String name;
        private final String guid;
        private final MerchantType type;

        // optional parameters
        private List<Location> locations;

        public MerchantBuilder(String name, String guid, MerchantType type) {
            this.name = name;
            this.guid = guid;
            this.type = type;
        }

        public MerchantBuilder locations(List<Location> locations) {
            this.locations = locations;
            return this;
        }

        public Merchant build() {
            return new Merchant(this);
        }
    }
}
