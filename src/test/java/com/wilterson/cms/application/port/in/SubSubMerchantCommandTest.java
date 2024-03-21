/*
 * Copyright 2024 Wilterson Franco
 */

package com.wilterson.cms.application.port.in;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import com.wilterson.cms.common.cache.CacheManager;
import com.wilterson.cms.common.cache.CachedEntity;
import jakarta.validation.ConstraintViolation;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
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
public class SubSubMerchantCommandTest {

    @Autowired
    private LocalValidatorFactoryBean validator;

    @MockBean
    private CacheManager cacheManager;

    @BeforeEach
    void setup() {
        doReturn(Optional.of(new CachedEntity("CachedName", "CACHED-GUID"))).when(cacheManager).getEntityByName("CachedName");
    }

    @Test
    void whenCreateHomeDepot_thenNameShouldBeHomeDepot() {

        // given
        var locationCommand = new LocationCommand("CAN", true);
        var theHomeDepot = "The Home Depot";
        var locationCommands = Collections.singletonList(locationCommand);

        // when
        var subMerchantCommand = new SubMerchantCommand(theHomeDepot, locationCommands);

        // then
        assertThat(subMerchantCommand.name()).isEqualTo("The Home Depot");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void whenBlankName_thenShouldListViolation(String emptyName) {

        // given
        SubMerchantCommand subMerchantCommand = new SubMerchantCommand(emptyName, Collections.singletonList(new LocationCommand("CAN", true)));

        // when
        Set<ConstraintViolation<SubMerchantCommand>> violations = validator.validate(subMerchantCommand);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("must not be blank");
    }

    @Test
    void whenNotUniqueName_thenShouldListViolation() {

        // given
        SubMerchantCommand subMerchantCommand = new SubMerchantCommand("CachedName", Collections.singletonList(new LocationCommand("CAN", true)));

        // when
        Set<ConstraintViolation<SubMerchantCommand>> violations = validator.validate(subMerchantCommand);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Merchant name must be unique");
    }

    @Test
    void whenDefaultLocationMissing_thenShouldListViolation() {

        // given
        SubMerchantCommand subMerchantCommand = new SubMerchantCommand("MerchantName", Collections.singletonList(new LocationCommand("CAN", false)));

        // when
        Set<ConstraintViolation<SubMerchantCommand>> violations = validator.validate(subMerchantCommand);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Default location is required");
    }

    @Test
    void whenLocationDuplicated_thenShouldListViolation() {

        // given
        List<LocationCommand> locations = List.of(new LocationCommand("CAN", false), new LocationCommand("CAN", true));
        SubMerchantCommand subMerchantCommand = new SubMerchantCommand("MerchantName", locations);

        // when
        Set<ConstraintViolation<SubMerchantCommand>> violations = validator.validate(subMerchantCommand);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Merchant already has location");
    }
}
