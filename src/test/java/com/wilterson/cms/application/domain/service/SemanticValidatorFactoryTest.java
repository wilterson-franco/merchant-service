package com.wilterson.cms.application.domain.service;

import static com.wilterson.cms.assertJ.IssueAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

import com.wilterson.cms.application.domain.model.Location.LocationBuilder;
import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.application.domain.model.Merchant.MerchantBuilder;
import com.wilterson.cms.application.domain.model.MerchantType;
import com.wilterson.cms.common.cache.CacheManager;
import com.wilterson.cms.common.cache.CachedEntity;
import com.wilterson.cms.common.validation.Issue;
import com.wilterson.cms.common.validation.SemanticValidatorFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SemanticValidatorFactoryTest {

    private Merchant merchant;
    private SemanticValidatorFactory semanticValidatorFactory;

    @BeforeEach
    void setup() {
        merchant = new MerchantBuilder("NAME", "GUID", MerchantType.SUB_MERCHANT)
                .locations(Collections.singleton(new LocationBuilder("CAN").defaultLocation(true).build()))
                .build();

        semanticValidatorFactory = new SemanticValidatorFactory(new CacheManager(Set.of(new CachedEntity("NAME", "GUID"))));
    }

    @Test
    void whenDuplicatedGuid_thenSemanticValidationShouldCatchIssue() {

        // given
        Set<Issue> issues = new HashSet<>();

        // when
        var uniqueGuid = semanticValidatorFactory.getUniqueGuid();
        uniqueGuid.validate(merchant, issues);

        // then
        assertThat(issues).hasSize(1);
        assertThat(new ArrayList<>(issues).getFirst()).hasDescription("Merchant GUID must be unique.");
    }

    @Test
    void whenDuplicateName_thenSemanticValidationShouldCatchIssue() {

        // given
        Set<Issue> issues = new HashSet<>();

        // when
        var uniqueName = semanticValidatorFactory.getUniqueName();
        uniqueName.validate(merchant, issues);

        // then
        assertThat(issues).hasSize(1);
        assertThat(new ArrayList<>(issues).getFirst()).hasDescription("Merchant name must be unique.");
    }

    @Test
    void whenNonLocation_thenSemanticValidationShouldCatchIssue() {

        // given
        Set<Issue> issues = new HashSet<>();
        var missingLocationsMerchant = new MerchantBuilder("NAME", "GUID", MerchantType.SUB_MERCHANT).build();

        // when
        var defaultLocation = semanticValidatorFactory.getDefaultLocation();
        defaultLocation.validate(missingLocationsMerchant, issues);

        // then
        assertThat(issues).hasSize(1);
        assertThat(new ArrayList<>(issues).getFirst()).hasDescription("Default location is required.");
    }

    @Test
    void whenNonDefaultLocation_thenSemanticValidationShouldCatchIssue() {

        // given
        Set<Issue> issues = new HashSet<>();
        var missingDefaultLocationMerchant = new MerchantBuilder("NAME", "GUID", MerchantType.SUB_MERCHANT)
                .locations(Collections.singleton(new LocationBuilder("CAN").defaultLocation(false).build()))
                .build();

        // when
        var defaultLocation = semanticValidatorFactory.getDefaultLocation();
        defaultLocation.validate(missingDefaultLocationMerchant, issues);

        // then
        assertThat(issues).hasSize(1);
        assertThat(new ArrayList<>(issues).getFirst()).hasDescription("Default location is required.");
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
        var defaultLocation = semanticValidatorFactory.getDefaultLocation();
        defaultLocation.validate(twoDefaultLocationsMerchant, issues);

        // then
        assertThat(issues).hasSize(1);
        assertThat(new ArrayList<>(issues).getFirst()).hasDescription("Only one location must be set as default.");
    }

    @Test
    void whenLocationNotAllowed_thenSemanticValidationShouldCatchIssue() {

        // given
        Set<Issue> issues = new HashSet<>();

        // when
        var locationNotAllowed = semanticValidatorFactory.getLocationNotAllowed();
        locationNotAllowed.validate(merchant, issues);

        // then
        assertThat(issues).hasSize(1);
        assertThat(new ArrayList<>(issues).getFirst()).hasDescription("Parent merchant can't have location.");
    }
}
