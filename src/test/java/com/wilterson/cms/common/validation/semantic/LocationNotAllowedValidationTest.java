package com.wilterson.cms.common.validation.semantic;

import static org.assertj.core.api.Assertions.assertThat;

import com.wilterson.cms.application.domain.model.Location.LocationBuilder;
import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.application.domain.model.Merchant.MerchantBuilder;
import com.wilterson.cms.application.domain.model.MerchantType;
import com.wilterson.cms.assertJ.IssueAssert;
import com.wilterson.cms.common.validation.Issue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class LocationNotAllowedValidationTest {

    private final LocationNotAllowedValidation locationNotAllowedValidation = new LocationNotAllowedValidation();

    @Test
    void whenLocationNotAllowed_thenSemanticValidationShouldCatchIssue() {

        // given
        Set<Issue> issues = new HashSet<>();
        Merchant merchant = new MerchantBuilder("NAME", "GUID", MerchantType.SUB_MERCHANT)
                .locations(Collections.singleton(new LocationBuilder("CAN").defaultLocation(true).build()))
                .build();

        // when
        locationNotAllowedValidation.validate(merchant, issues);

        // then
        assertThat(issues).hasSize(1);
        IssueAssert.assertThat(new ArrayList<>(issues).getFirst()).hasDescription("Parent merchant can't have location.");
    }
}