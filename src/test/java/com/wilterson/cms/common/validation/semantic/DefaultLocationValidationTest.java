package com.wilterson.cms.common.validation.semantic;

import static org.assertj.core.api.Assertions.assertThat;

import com.wilterson.cms.application.domain.model.Location.LocationBuilder;
import com.wilterson.cms.application.domain.model.Merchant.MerchantBuilder;
import com.wilterson.cms.application.domain.model.MerchantType;
import com.wilterson.cms.assertJ.IssueAssert;
import com.wilterson.cms.common.validation.Issue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DefaultLocationValidationTest {

    private final DefaultLocationValidation defaultLocationValidation = new DefaultLocationValidation();

    @Test
    void whenNonLocation_thenSemanticValidationShouldCatchIssue() {

        // given
        Set<Issue> issues = new HashSet<>();
        var missingLocationsMerchant = new MerchantBuilder("NAME", "GUID", MerchantType.SUB_MERCHANT).build();

        // when
        defaultLocationValidation.validate(missingLocationsMerchant, issues);

        // then
        assertThat(issues).hasSize(1);
        IssueAssert.assertThat(new ArrayList<>(issues).getFirst()).hasDescription("Default location is required.");
    }

    @Test
    void whenNonDefaultLocation_thenSemanticValidationShouldCatchIssue() {

        // given
        Set<Issue> issues = new HashSet<>();
        var missingDefaultLocationMerchant = new MerchantBuilder("NAME", "GUID", MerchantType.SUB_MERCHANT)
                .locations(Collections.singleton(new LocationBuilder("CAN").defaultLocation(false).build()))
                .build();

        // when
        defaultLocationValidation.validate(missingDefaultLocationMerchant, issues);

        // then
        assertThat(issues).hasSize(1);
        IssueAssert.assertThat(new ArrayList<>(issues).getFirst()).hasDescription("Default location is required.");
    }

    @Test
    void whenMultipleDefaultLocation_thenSemanticValidationShouldCatchIssue() {

        // given
        Set<Issue> issues = new HashSet<>();
        var twoDefaultLocations = Set.of(new LocationBuilder("CAN").defaultLocation(true).build(), new LocationBuilder("USA").defaultLocation(true).build());
        var twoDefaultLocationsMerchant = new MerchantBuilder("NAME", "GUID", MerchantType.SUB_MERCHANT)
                .locations(twoDefaultLocations)
                .build();

        // when
        defaultLocationValidation.validate(twoDefaultLocationsMerchant, issues);

        // then
        assertThat(issues).hasSize(1);
        IssueAssert.assertThat(new ArrayList<>(issues).getFirst()).hasDescription("Only one location must be set as default.");
    }
}