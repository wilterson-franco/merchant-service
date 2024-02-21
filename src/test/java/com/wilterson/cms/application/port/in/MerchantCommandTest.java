package com.wilterson.cms.application.port.in;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class MerchantCommandTest {

    @Test
    void whenCreateHomeDepot_thenNameShouldBeHomeDepot() {

        // given
        var locationCommand = new LocationCommand("CAN", true);
        var theHomeDepot = "The Home Depot";
        var merchantType = MerchantTypeCommand.SINGLE_MERCHANT;
        var locationCommands = Collections.singleton(locationCommand);

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
        var locationCommand = new LocationCommand("CAN", true);
        var merchantType = MerchantTypeCommand.SINGLE_MERCHANT;
        var locationCommands = Collections.singleton(locationCommand);

        // when
        // then
        assertThrows(ConstraintViolationException.class, () -> new MerchantCommand(emptyName, merchantType, locationCommands));
    }

    @DisplayName("given the merchant type " +
            "when non-parent merchants are created " +
            "then the type should be set accordingly")
    @ParameterizedTest
    @EnumSource(value = MerchantTypeCommand.class, names = {"SINGLE_MERCHANT", "SUB_MERCHANT"})
    void whenNonParentMerchant_thenTypeShouldBeSetProperly(MerchantTypeCommand type) {

        // given
        var locationCommand = new LocationCommand("CAN", true);
        var locationCommands = Collections.singleton(locationCommand);
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
        Set<LocationCommand> locationCommands = Collections.emptySet();

        // when
        var merchantCommand = new MerchantCommand(merchantName, type, locationCommands);

        // then
        assertThat(merchantCommand.type()).isEqualTo(type);
    }

    @Test
    void whenCreateMerchantInvalidType_thenShouldThrowException() {

        // given
        var merchantName = "MerchantName";
        var locationCommands = Collections.singleton(new LocationCommand("CAN", true));

        // when
        var exception = assertThrows(ConstraintViolationException.class, () -> new MerchantCommand(merchantName, null, locationCommands));
        assertThat(exception).hasMessage("type: Type can't be null");
    }
}
