package com.wilterson.cms.application.domain.model;

import static com.wilterson.cms.application.domain.model.MerchantType.SUB_MERCHANT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import com.wilterson.cms.application.domain.model.Location.LocationBuilder;
import com.wilterson.cms.application.domain.model.SubMerchant.MerchantBuilder;
import com.wilterson.cms.common.cache.CacheManager;
import com.wilterson.cms.common.cache.CachedEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * This JUnit requires that a Jakarta Bean Validator and a
 * Cache Manager beans are available in the Spring Context.
 */
@SpringBootTest(classes = {LocalValidatorFactoryBean.class})
class SubMerchantTest {

    @Autowired
    private Validator validator;

    @MockBean
    private CacheManager cacheManager;

    @BeforeEach
    void setup() {
        CachedEntity cachedEntity = new CachedEntity("CachedName", "CACHED-GUID");
        doReturn(Optional.of(cachedEntity)).when(cacheManager).getEntityByName("CachedName");
        doReturn(Optional.of(cachedEntity)).when(cacheManager).getEntityByGuid("CACHED-GUID");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void whenBlankName_thenItShouldListViolation(String emptyName) {

        // given
        SubMerchant subMerchant = new MerchantBuilder(emptyName, "GUID", MerchantType.SINGLE_MERCHANT)
                .locations(Collections.singletonList(new LocationBuilder("CAN").defaultLocation(true).build()))
                .build();

        // when
        Set<ConstraintViolation<SubMerchant>> violations = validator.validate(subMerchant);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("must not be blank");
    }

    @DisplayName("given the merchant type " +
            "when non-parent merchants are created " +
            "then the type should be set accordingly")
    @ParameterizedTest
    @EnumSource(value = MerchantType.class, names = {"SINGLE_MERCHANT", "SUB_MERCHANT"})
    void whenNonParentMerchant_thenTypeShouldBeSetProperly(MerchantType type) {

        // given
        var location = new LocationBuilder("CAN").defaultLocation(true).build();
        var locations = Collections.singletonList(location);
        var merchantName = "MerchantName";

        // when
        var merchant = new MerchantBuilder(merchantName, "GUID", type)
                .locations(locations)
                .build();

        // then
        assertThat(merchant.getType()).isEqualTo(type);
    }

    @DisplayName("given the merchant type " +
            "when parent merchants are created " +
            "then the type should be set accordingly")
    @ParameterizedTest
    @EnumSource(value = MerchantType.class, names = {"PARTNER", "MULTI_MERCHANT"})
    void whenParentMerchant_thenTypeShouldBeSetProperly(MerchantType type) {

        // given
        var merchantName = "MerchantName";
        Set<Location> locations = Collections.emptySet();

        // when
        var merchant = new MerchantBuilder(merchantName, "GUID", type).build();

        // then
        assertThat(merchant.getType()).isEqualTo(type);
    }

    @Test
    void whenCreateMerchantInvalidType_thenShouldListViolation() {

        // given
        SubMerchant subMerchant = new MerchantBuilder("MerchantName", "GUID", null)
                .locations(Collections.singletonList(new LocationBuilder("CAN").defaultLocation(true).build()))
                .build();

        // when
        Set<ConstraintViolation<SubMerchant>> violations = validator.validate(subMerchant);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("must not be null");
    }

    @Test
    void whenGuidInLowerCase_thenShouldListViolation() {

        // given
        SubMerchant subMerchant = new MerchantBuilder("MerchantName", "abcdef", SUB_MERCHANT)
                .locations(Collections.singletonList(new LocationBuilder("CAN").defaultLocation(true).build()))
                .build();

        // when
        Set<ConstraintViolation<SubMerchant>> violations = validator.validate(subMerchant);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Merchant GUID must be all upper case");
    }

    @Test
    void whenNotUniqueName_thenShouldListViolation() {

        // given
        SubMerchant subMerchant = new MerchantBuilder("CachedName", "GUID", SUB_MERCHANT)
                .locations(Collections.singletonList(new LocationBuilder("CAN").defaultLocation(true).build()))
                .build();

        // when
        Set<ConstraintViolation<SubMerchant>> violations = validator.validate(subMerchant);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Merchant name must be unique");
    }

    @Test
    void whenNotUniqueGuid_thenShouldListViolation() {

        // given
        SubMerchant subMerchant = new MerchantBuilder("MerchantName", "CACHED-GUID", SUB_MERCHANT)
                .locations(Collections.singletonList(new LocationBuilder("CAN").defaultLocation(true).build()))
                .build();

        // when
        Set<ConstraintViolation<SubMerchant>> violations = validator.validate(subMerchant);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Merchant GUID must be unique");
    }

    @Test
    void whenDefaultLocationMissing_thenShouldListViolation() {

        // given
        SubMerchant subMerchant = new MerchantBuilder("MerchantName", "GUID", SUB_MERCHANT)
                .locations(Collections.singletonList(new LocationBuilder("CAN").defaultLocation(false).build()))
                .build();

        // when
        Set<ConstraintViolation<SubMerchant>> violations = validator.validate(subMerchant);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Default location is required");
    }

    @Test
    void whenLocationDuplicated_thenShouldListViolation() {

        // given
        List<Location> locations = List.of(
                new LocationBuilder("CAN").defaultLocation(false).build(),
                new LocationBuilder("CAN").defaultLocation(true).build());

        SubMerchant subMerchant = new MerchantBuilder("MerchantName", "GUID", SUB_MERCHANT)
                .locations(locations)
                .build();

        // when
        Set<ConstraintViolation<SubMerchant>> violations = validator.validate(subMerchant);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Merchant already has location");
    }
}