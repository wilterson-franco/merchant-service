package com.wilterson.cms.common.validation.semantic;

import static org.assertj.core.api.Assertions.assertThat;

import com.wilterson.cms.application.domain.model.Location.LocationBuilder;
import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.application.domain.model.Merchant.MerchantBuilder;
import com.wilterson.cms.application.domain.model.MerchantType;
import com.wilterson.cms.assertJ.IssueAssert;
import com.wilterson.cms.common.cache.CacheManager;
import com.wilterson.cms.common.cache.CachedEntity;
import com.wilterson.cms.common.validation.Issue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UniqueGuidValidationTest {

    private UniqueGuidValidation uniqueGuidValidation;

    @BeforeEach
    void setup() {
        uniqueGuidValidation = new UniqueGuidValidation(new CacheManager(Set.of(new CachedEntity("NAME", "GUID"))));
    }

    @Test
    void whenDuplicatedGuid_thenSemanticValidationShouldCatchIssue() {

        // given
        Set<Issue> issues = new HashSet<>();
        Merchant merchant = new MerchantBuilder("NAME", "GUID", MerchantType.SUB_MERCHANT)
                .locations(Collections.singleton(new LocationBuilder("CAN").defaultLocation(true).build()))
                .build();

        // when
        uniqueGuidValidation.validate(merchant, issues);

        // then
        assertThat(issues).hasSize(1);
        IssueAssert.assertThat(new ArrayList<>(issues).getFirst()).hasDescription("Merchant GUID must be unique.");
    }
}