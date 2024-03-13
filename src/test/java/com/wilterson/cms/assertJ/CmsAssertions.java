/*
 * Copyright 2024 Wilterson Franco
 */
package com.wilterson.cms.assertJ;

import com.wilterson.cms.application.domain.model.Location;
import com.wilterson.cms.application.domain.model.SubMerchant;
import com.wilterson.cms.common.validation.Issue;

public abstract class CmsAssertions {

    public static <T extends SubMerchant> MerchantAssert assertThat(T actual) {
        return MerchantAssert.assertThat(actual);
    }

    public static <T extends Issue> IssueAssert assertThat(T actual) {
        return IssueAssert.assertThat(actual);
    }

    public static <T extends Location> LocationAssert assertThat(T actual) {
        return LocationAssert.assertThat(actual);
    }
}
