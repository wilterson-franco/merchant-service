package com.wilterson.cms.assertJ;

import com.wilterson.cms.application.domain.model.Location;
import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.common.validation.Issue;
import com.wilterson.cms.common.validation.SemanticException;

public abstract class CmsAssertions {

    public static <T extends SemanticException> SemanticExceptionAssert assertThat(T actual) {
        return SemanticExceptionAssert.assertThat(actual);
    }

    public static <T extends Merchant> MerchantAssert assertThat(T actual) {
        return MerchantAssert.assertThat(actual);
    }

    public static <T extends Issue> IssueAssert assertThat(T actual) {
        return IssueAssert.assertThat(actual);
    }

    public static <T extends Location> LocationAssert assertThat(T actual) {
        return LocationAssert.assertThat(actual);
    }
}
