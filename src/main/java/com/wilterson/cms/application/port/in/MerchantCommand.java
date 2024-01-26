package com.wilterson.cms.application.port.in;

import com.wilterson.cms.application.domain.model.Location;
import com.wilterson.cms.application.domain.model.MerchantType;
import com.wilterson.cms.common.validation.SemanticValidator;
import com.wilterson.cms.common.validation.SyntacticValidator;
import com.wilterson.cms.common.validation.Validatable;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import org.springframework.util.CollectionUtils;

public record MerchantCommand(
        @NotBlank(message = "{merchant.name.required}") String name,
        @NotNull(message = "Type can't be null") MerchantType type,
        Collection<Location> locations) implements Validatable {

    public MerchantCommand(String name, MerchantType type, Collection<Location> locations) {

        this.name = name;
        this.type = type;
        this.locations = locations;

        // syntactic validation (input fields)
        SyntacticValidator.validate(this);

        // semantic validations (business rules)
        SemanticValidator<MerchantCommand> semanticValidator = new ValidateMerchantCommand();
        semanticValidator.validate(this);
    }

    private static class ValidateMerchantCommand implements SemanticValidator<MerchantCommand> {

        @Override
        public void validate(MerchantCommand model) {
            if (isParentMerchant(model.type())) {
                locationNotAllowed(model.locations());
            } else {
                requireDefaultLocation(model.locations());
            }
        }

        private static boolean isParentMerchant(MerchantType type) {
            return type != MerchantType.SINGLE_MERCHANT && type != MerchantType.SUB_MERCHANT;
        }

        private static void locationNotAllowed(Collection<Location> locations) {
            if (!CollectionUtils.isEmpty(locations)) {
                throw new IllegalArgumentException("Parent merchant can't have location.");
            }
        }

        private static void requireDefaultLocation(Collection<Location> locations) {
            if (locations.stream().noneMatch(Location::isDefault)) {
                throw new IllegalArgumentException("Default location is mandatory");
            }
        }
    }
}
