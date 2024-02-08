package com.wilterson.cms.common.validation;

import com.wilterson.cms.application.domain.model.Location;
import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.application.domain.model.MerchantType;
import com.wilterson.cms.application.port.in.MerchantCommand;
import com.wilterson.cms.application.port.in.SemanticValidatorUseCase;
import com.wilterson.cms.common.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
public final class SemanticValidatorFactory {

    private final Cache cache;

    /**
     * Check if the merchant's name is unique across all merchants.
     */
    private final SemanticValidatorUseCase<Merchant> uniqueName = (merchant) ->
            cache.getEntityByName(merchant.getName()).orElseThrow(() -> new IllegalArgumentException("Merchant name must be unique"));

    /**
     * Check if the merchant's GUID is unique across all merchants.
     */
    private final SemanticValidatorUseCase<Merchant> uniqueGuid = (merchant) ->
            cache.getEntityByGuid(merchant.getGuid()).orElseThrow(() -> new IllegalArgumentException("Merchant GUID must be unique"));

    /**
     * Check if one and only one default location is present in the merchant object.
     */
    private final SemanticValidatorUseCase<Merchant> defaultLocation = (merchant) -> {
        var count = merchant.getLocations().stream().filter(Location::isDefault).count();
        if (count < 1) {
            throw new IllegalArgumentException("Default location is required");
        } else if (count > 1) {
            throw new IllegalArgumentException("Only one location must be set as default");
        }
    };

    /**
     * Check if any location is present in the merchant object.
     */
    private final SemanticValidatorUseCase<Merchant> locationNotAllowed = (merchant) -> {
        if (!CollectionUtils.isEmpty(merchant.getLocations())) {
            throw new IllegalArgumentException("Parent merchant can't have location.");
        }
    };

    private SemanticValidatorUseCase<Merchant> subMerchantValidator() {
        return uniqueName
                .andThen(uniqueGuid)
                .andThen(defaultLocation);
    }

    private SemanticValidatorUseCase<Merchant> multiMerchantValidator() {
        return uniqueName
                .andThen(uniqueGuid)
                .andThen(locationNotAllowed);
    }

    private SemanticValidatorUseCase<Merchant> partnerValidator() {
        return uniqueName
                .andThen(uniqueGuid)
                .andThen(locationNotAllowed);
    }

    public SemanticValidatorUseCase<Merchant> validator(MerchantCommand merchant) {
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
