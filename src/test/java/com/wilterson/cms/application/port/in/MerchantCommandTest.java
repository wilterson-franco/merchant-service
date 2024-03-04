package com.wilterson.cms.application.port.in;

import static com.wilterson.cms.application.port.in.MerchantTypeCommand.SINGLE_MERCHANT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

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
public class MerchantCommandTest {

    @Autowired
    private Validator validator;

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
        var merchantType = SINGLE_MERCHANT;
        var locationCommands = Collections.singletonList(locationCommand);

        // when
        var merchantCommand = new MerchantCommand(theHomeDepot, merchantType, locationCommands);

        // then
        assertThat(merchantCommand.name()).isEqualTo("The Home Depot");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void whenBlankName_thenItShouldThrowException(String emptyName) {

        // given
        MerchantCommand merchantCommand = new MerchantCommand(emptyName, SINGLE_MERCHANT, Collections.singletonList(new LocationCommand("CAN", true)));

        // when
        Set<ConstraintViolation<MerchantCommand>> violations = validator.validate(merchantCommand);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("must not be blank");
    }

    @DisplayName("given the merchant type " +
            "when non-parent merchants are created " +
            "then the type should be set accordingly")
    @ParameterizedTest
    @EnumSource(value = MerchantTypeCommand.class, names = {"SINGLE_MERCHANT", "SUB_MERCHANT"})
    void whenNonParentMerchant_thenTypeShouldBeSetProperly(MerchantTypeCommand type) {

        // given
        var locationCommand = new LocationCommand("CAN", true);
        var locationCommands = Collections.singletonList(locationCommand);
        var merchantName = "MerchantName";

        // when
        var merchantCommand = new MerchantCommand(merchantName, type, locationCommands);

        // then
        assertThat(merchantCommand.type()).isEqualTo(type);
    }

    @DisplayName("given the merchant type " +
            "when parent merchants are created " +
            "then the type should be set accordingly")
    @ParameterizedTest
    @EnumSource(value = MerchantTypeCommand.class, names = {"PARTNER", "MULTI_MERCHANT"})
    void whenParentMerchant_thenTypeShouldBeSetProperly(MerchantTypeCommand type) {

        // given
        var merchantName = "MerchantName";
        List<LocationCommand> locationCommands = Collections.emptyList();

        // when
        var merchantCommand = new MerchantCommand(merchantName, type, locationCommands);

        // then
        assertThat(merchantCommand.type()).isEqualTo(type);
    }

    @Test
    void whenCreateMerchantInvalidType_thenShouldListViolation() {

        // given
        MerchantCommand merchantCommand = new MerchantCommand("MerchantName", null, Collections.singletonList(new LocationCommand("CAN", true)));

        // when
        Set<ConstraintViolation<MerchantCommand>> violations = validator.validate(merchantCommand);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Type can't be null");
    }

    @Test
    void whenNotUniqueName_thenShouldListViolation() {

        // given
        MerchantCommand merchantCommand = new MerchantCommand("CachedName", SINGLE_MERCHANT, Collections.singletonList(new LocationCommand("CAN", true)));

        // when
        Set<ConstraintViolation<MerchantCommand>> violations = validator.validate(merchantCommand);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Merchant name must be unique");
    }

    @Test
    void whenDefaultLocationMissing_thenShouldListViolation() {

        // given
        MerchantCommand merchantCommand = new MerchantCommand("MerchantName", SINGLE_MERCHANT, Collections.singletonList(new LocationCommand("CAN", false)));

        // when
        Set<ConstraintViolation<MerchantCommand>> violations = validator.validate(merchantCommand);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Default location is required");
    }

    @Test
    void whenLocationDuplicated_thenShouldListViolation() {

        // given
        List<LocationCommand> locations = List.of(new LocationCommand("CAN", false), new LocationCommand("CAN", true));
        MerchantCommand merchantCommand = new MerchantCommand("MerchantName", SINGLE_MERCHANT, locations);

        // when
        Set<ConstraintViolation<MerchantCommand>> violations = validator.validate(merchantCommand);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Merchant already has location");
    }
}
