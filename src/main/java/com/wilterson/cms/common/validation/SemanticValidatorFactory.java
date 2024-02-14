package com.wilterson.cms.common.validation;

import com.wilterson.cms.application.domain.model.Location;
import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.application.domain.model.MerchantType;
import com.wilterson.cms.application.port.in.MerchantCommand;
import com.wilterson.cms.common.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public final class SemanticValidatorFactory {

    private final CacheManager cacheManager;

    public SemanticValidatorFactory(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     * Check if the merchant's name is unique across all merchants.
     */
    private final SemanticValidator<Merchant> uniqueName = uniqueNameValidator();

    /**
     * Check if the merchant's GUID is unique across all merchants.
     */
    private final SemanticValidator<Merchant> uniqueGuid = uniqueGuidValidator();

    /**
     * Check if one and only one default location is present in the merchant object.
     */
    private final SemanticValidator<Merchant> defaultLocation = defaultLocationValidator();

    /**
     * Check if any location is present in the merchant object.
     */
    private final SemanticValidator<Merchant> locationNotAllowed = locationNotAllowedValidator();

    private SemanticValidator<Merchant> subMerchantSemanticValidator() {
        return uniqueName
                .andThen(uniqueGuid)
                .andThen(defaultLocation);
    }

    private SemanticValidator<Merchant> multiMerchantSemanticValidator() {
        return uniqueName
                .andThen(uniqueGuid)
                .andThen(locationNotAllowed);
    }

    private SemanticValidator<Merchant> partnerSemanticValidator() {
        return uniqueName
                .andThen(uniqueGuid)
                .andThen(locationNotAllowed);
    }

    private SemanticValidator<Merchant> uniqueNameValidator() {
        return (merchant) -> cacheManager.getEntityByName(merchant.getName()).ifPresent((entity) -> {
            throw new UniqueNameException("Merchant name must be unique");
        });
    }

    private SemanticValidator<Merchant> uniqueGuidValidator() {
        return (merchant) -> cacheManager.getEntityByGuid(merchant.getGuid()).ifPresent((entity) -> {
            throw new UniqueGuidException("Merchant GUID must be unique");
        });
    }

    private static SemanticValidator<Merchant> defaultLocationValidator() {
        return (merchant) -> {
            var count = merchant.getLocations().stream().filter(Location::isDefault).count();
            if (count < 1) {
                throw new LocationValidationException("Default location is required");
            } else if (count > 1) {
                throw new LocationValidationException("Only one location must be set as default");
            }
        };
    }

    private static SemanticValidator<Merchant> locationNotAllowedValidator() {
        return (merchant) -> {
            if (!CollectionUtils.isEmpty(merchant.getLocations())) {
                throw new LocationValidationException("Parent merchant can't have location.");
            }
        };
    }

    public SemanticValidator<Merchant> validator(MerchantCommand merchant) {
        if (merchant.type() == MerchantType.SUB_MERCHANT) {
            return subMerchantSemanticValidator();
        }
        if (merchant.type() == MerchantType.MULTI_MERCHANT) {
            return multiMerchantSemanticValidator();
        }
        if (merchant.type() == MerchantType.PARTNER) {
            return partnerSemanticValidator();
        }

        // TODO: shouldn't throw an exception?

        // Empty consumer for no validations
        return (model) -> {
        };
    }
}
