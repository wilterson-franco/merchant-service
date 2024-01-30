package com.wilterson.cms.application.domain.model;

import com.wilterson.cms.common.StringGenerator;
import com.wilterson.cms.application.port.in.SemanticValidatorUseCase;
import com.wilterson.cms.common.validation.SyntacticValidator;
import com.wilterson.cms.common.validation.Validatable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import lombok.Data;

@Data
public class Merchant implements Validatable {

    private static final int GUID_LENGTH = 16;

    private final String guid = StringGenerator.generate(GUID_LENGTH);
    @NotBlank
    private final String name;
    @NotNull
    private final MerchantType type;
    private final Collection<Location> locations;

    @NotNull
    private SemanticValidatorUseCase<Merchant> validator;

    private Merchant(MerchantBuilder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.locations = builder.locations;
        this.validator = builder.validator;

        // syntactic validations (input validations)
        SyntacticValidator.validate(this);

        // semantic validations (business rules)
        validator.validate(this);
    }

    public static class MerchantBuilder {

        // mandatory parameters
        private final String name;
        private final MerchantType type;
        private final SemanticValidatorUseCase<Merchant> validator;

        // optional parameters
        private Collection<Location> locations;

        public MerchantBuilder(String name, MerchantType type, SemanticValidatorUseCase<Merchant> validator) {
            this.name = name;
            this.type = type;
            this.validator = validator;
        }

        public MerchantBuilder locations(Collection<Location> locations) {
            this.locations = locations;
            return this;
        }

        public Merchant build() {
            return new Merchant(this);
        }
    }
}
