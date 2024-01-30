package com.wilterson.cms.application.domain.service;

import com.wilterson.cms.application.domain.model.Location;
import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.application.domain.model.MerchantType;
import com.wilterson.cms.application.port.in.MerchantCommand;
import com.wilterson.cms.application.port.in.SemanticValidatorUseCase;
import org.springframework.util.CollectionUtils;

abstract class SemanticValidatorFactory {

    /**
     * Checks if the merchant's name is unique accross all merchants.
     */
    private static final SemanticValidatorUseCase<Merchant> uniqueName = (merchant) -> {

        // TODO: implement unique name check

        boolean isUniqueName = true;

        if (!isUniqueName) {
            throw new IllegalArgumentException("Merchant name must be unique");
        }
    };

    /**
     * Checks if one and only one default location is present in the merchant object.
     */
    private static final SemanticValidatorUseCase<Merchant> defaultLocation = (merchant) -> {
        var count = merchant.getLocations().stream().filter(Location::isDefault).count();
        if (count < 1) {
            throw new IllegalArgumentException("Default location is required");
        } else if (count > 1) {
            throw new IllegalArgumentException("Only one location must be set as default");
        }
    };

    /**
     * Checks if any location is present in the merchant object.
     */
    private static final SemanticValidatorUseCase<Merchant> locationNotAllowed = (merchant) -> {
        if (!CollectionUtils.isEmpty(merchant.getLocations())) {
            throw new IllegalArgumentException("Parent merchant can't have location.");
        }
    };

    private static SemanticValidatorUseCase<Merchant> subMerchantValidator() {
        return uniqueName.andThen(defaultLocation);
    }

    private static SemanticValidatorUseCase<Merchant> multiMerchantValidator() {
        return uniqueName.andThen(locationNotAllowed);
    }

    private static SemanticValidatorUseCase<Merchant> partnerValidator() {
        return uniqueName.andThen(locationNotAllowed);
    }

    static SemanticValidatorUseCase<Merchant> validator(MerchantCommand merchant) {
        if (merchant.type() == MerchantType.SUB_MERCHANT) {
            return subMerchantValidator();
        }
        if (merchant.type() == MerchantType.MULTI_MERCHANT) {
            return multiMerchantValidator();
        }
        if (merchant.type() == MerchantType.PARTNER) {
            return partnerValidator();
        }

        // TODO: shouldn't throw an exception?

        // Empty consumer for no validations
        return (model) -> {
        };
    }
}
