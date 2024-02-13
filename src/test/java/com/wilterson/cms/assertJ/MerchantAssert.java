package com.wilterson.cms.assertJ;

import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.application.domain.model.MerchantType;
import org.assertj.core.api.AbstractAssert;

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
            failWithMessage("Expected merchant to have name %s but was %s", merchantName, actual.getName());
        }
        return this;
    }

    public MerchantAssert typeEqualsTo(MerchantType merchantType) {
        isNotNull();
        if (actual.getType() != merchantType) {
            failWithMessage("Expected merchant to have name %s but was %s", merchantType, actual.getType());
        }
        return this;
    }
}
