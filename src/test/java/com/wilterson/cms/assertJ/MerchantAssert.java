package com.wilterson.cms.assertJ;

import com.wilterson.cms.application.domain.model.Location;
import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.application.domain.model.MerchantType;
import java.util.List;
import org.assertj.core.api.AbstractAssert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class MerchantAssert extends AbstractAssert<MerchantAssert, Merchant> {

    public MerchantAssert(Merchant merchant) {
        super(merchant, MerchantAssert.class);
    }

    public static MerchantAssert assertThat(Merchant actual) {
        return new MerchantAssert(actual);
    }

    public MerchantAssert nameEqualsTo(String merchantName) {
        isNotNull();
        if (!actual.getName().equals(merchantName)) {
            failWithMessage("Expected merchant to have name %s but was %s.", merchantName, actual.getName());
        }
        return this;
    }

    public MerchantAssert typeEqualsTo(MerchantType merchantType) {
        isNotNull();
        if (actual.getType() != merchantType) {
            failWithMessage("Expected merchant to have name %s but was %s.", merchantType, actual.getType());
        }
        return this;
    }

    public MerchantAssert guidEqualsTo(String guid) {
        isNotNull();
        if (!actual.getGuid().equals(guid)) {
            failWithMessage("Expected merchant to have guid %s but was %s.", guid, actual.getGuid());
        }
        return this;
    }

    public MerchantAssert hasGuid() {
        isNotNull();
        if (!StringUtils.hasText(actual.getGuid())) {
            failWithMessage("Expected merchant GUID to be not empty.");
        }
        return this;
    }

    public MerchantAssert hasDefaultLocation() {
        isNotNull();
        if (!CollectionUtils.isEmpty(actual.getLocations()) && actual.getLocations().stream().noneMatch(Location::isDefault)) {
            failWithMessage("Expected merchant to have one default location.");
        }
        return this;
    }

    public MerchantAssert doesNotHaveLocations() {
        isNotNull();
        if (!CollectionUtils.isEmpty(actual.getLocations())) {
            failWithMessage("Expected merchant to not have locations.");
        }
        return this;
    }

    public MerchantAssert containsLocationByCountryCodeIgnoreCase(String countryCode) {
        isNotNull();
        if (!CollectionUtils.isEmpty(actual.getLocations())
                && actual.getLocations().stream().noneMatch(l -> l.getCountryCode().equalsIgnoreCase(countryCode))) {
            failWithMessage("Expected merchant to have location with countryCode %s.", countryCode);
        }
        return this;
    }

    public MerchantAssert hasLocationsSize(int size) {
        isNotNull();
        List<Location> actualLocations = actual.getLocations();
        int actualLocationsSize = !CollectionUtils.isEmpty(actualLocations) ? actualLocations.size() : 0;
        if (actualLocationsSize != size) {
            failWithMessage("Expected merchant to have %d locations but was %d.", size, actualLocationsSize);
        }
        return this;
    }
}
