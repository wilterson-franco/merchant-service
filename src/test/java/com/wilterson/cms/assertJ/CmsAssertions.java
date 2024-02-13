package com.wilterson.cms.assertJ;

import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.application.domain.service.ApplicationException;

public abstract class CmsAssertions {

    public static <T extends ApplicationException> ApplicationExceptionAssert assertThat(T actual) {
        return ApplicationExceptionAssert.assertThat(actual);
    }

    public static <T extends Merchant> MerchantAssert assertThat(T actual) {
        return MerchantAssert.assertThat(actual);
    }
}
