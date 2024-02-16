package com.wilterson.cms.common.validation;

import static com.wilterson.cms.common.validation.IssueFactory.issue;

import com.wilterson.cms.application.domain.model.Location;
import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.common.cache.CacheManager;
import java.util.Set;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public final class SemanticValidatorFactory {

    // TODO: The goal is to accumulate the issues in a collection, rather than throwing exceptions when an issue
    //  is found. This is to gather all issues in a collection and return all of them to the user, in one go.
    //  The approach implemented so far works, but it has this drawback of having to pass the collection of
    //  SemanticValidationException to the SemanticValidator::validate to store the issues. The approach that I'm
    //  more inclined to, which is not implemented yet, is to change the scope of the SemanticValidatorFactory
    //  bean to "request" and add a field "Collection<SemanticValidationException> issues". This won't cause any
    //  concurrency issues because the bean has a "request" scope, which means that Spring will hand a new instance
    //  over to each new thread. Each request will have it's own copy of the issues.

    private CacheManager cacheManager;

    public SemanticValidatorFactory(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     * Check if the merchant's name is unique across all merchants.
     */
    @Getter
    private final SemanticValidator<Merchant> uniqueName = (merchant, semanticIssues) -> cacheManager.getEntityByName(merchant.getName())
            .ifPresent((entity) -> semanticIssues.add(issue(IssueFactory.MERCHANT_NAME.getReasonCode(), "Merchant name must be unique.", false)));

    /**
     * Check if the merchant's GUID is unique across all merchants.
     */
    @Getter
    private final SemanticValidator<Merchant> uniqueGuid = (merchant, semanticIssues) -> cacheManager.getEntityByGuid(merchant.getGuid())
            .ifPresent((entity) -> semanticIssues.add(issue(IssueFactory.MERCHANT_GUID.getReasonCode(), "Merchant GUID must be unique.", false)));

    /**
     * Check if one and only one default location is present in the merchant object.
     */
    @Getter
    private final SemanticValidator<Merchant> defaultLocation = (merchant, semanticIssues) -> {

        var count = countDefaultLocations(merchant);

        if (count < 1) {
            semanticIssues.add(issue(IssueFactory.MERCHANT_LOCATIONS_DEFAULT.getReasonCode(), "Default location is required.", false));
        } else if (count > 1) {
            semanticIssues.add(issue(IssueFactory.MERCHANT_LOCATIONS_DEFAULT.getReasonCode(), "Only one location must be set as default.", false));
        }
    };

    /**
     * Check if any location is present in the merchant object.
     */
    @Getter
    private final SemanticValidator<Merchant> locationNotAllowed = (merchant, semanticIssues) -> {
        if (!CollectionUtils.isEmpty(merchant.getLocations())) {
            semanticIssues.add(issue(IssueFactory.MERCHANT_LOCATIONS.getReasonCode(), "Parent merchant can't have location.", false));
        }
    };

    public SemanticValidator<Merchant> subMerchantSemanticValidator() {
        return uniqueName
                .andThen(uniqueGuid)
                .andThen(defaultLocation);
    }

    public SemanticValidator<Merchant> multiMerchantSemanticValidator() {
        return uniqueName
                .andThen(uniqueGuid)
                .andThen(locationNotAllowed);
    }

    public SemanticValidator<Merchant> partnerSemanticValidator() {
        return uniqueName
                .andThen(uniqueGuid)
                .andThen(locationNotAllowed);
    }

    private static long countDefaultLocations(Merchant merchant) {

        Set<Location> locations = merchant.getLocations();

        if (!CollectionUtils.isEmpty(locations)) {
            return locations.stream().filter(Location::isDefault).count();
        }

        return 0;
    }
}
