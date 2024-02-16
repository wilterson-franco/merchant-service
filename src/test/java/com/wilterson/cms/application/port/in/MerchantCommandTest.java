package com.wilterson.cms.application.port.in;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wilterson.cms.application.domain.model.Location;
import com.wilterson.cms.application.domain.model.Location.LocationBuilder;
import com.wilterson.cms.application.domain.model.MerchantType;
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
        var location = new LocationBuilder("CAN").defaultLocation(true).build();
        var theHomeDepot = "The Home Depot";
        var merchantType = MerchantType.SINGLE_MERCHANT;
        var locations = Collections.singleton(location);

        // when
        MerchantCommand command = new MerchantCommand(theHomeDepot, merchantType, locations);

        // then
        assertThat(command.name()).isEqualTo("The Home Depot");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void whenBlankName_thenItShouldThrowException(String emptyName) {

        // given
        var location = new LocationBuilder("CAN").defaultLocation(true).build();
        var merchantType = MerchantType.SINGLE_MERCHANT;
        var locations = Collections.singleton(location);

        // when
        // then
        assertThrows(ConstraintViolationException.class, () -> new MerchantCommand(emptyName, merchantType, locations));
    }

    @DisplayName("given the merchant type " +
            "when non-parent merchants are created " +
            "then the type should be set accordingly")
    @ParameterizedTest
    @EnumSource(value = MerchantType.class, names = {"SINGLE_MERCHANT", "SUB_MERCHANT"})
    void whenNonParentMerchant_thenTypeShouldBeSetProperly(MerchantType type) {

        // given
        var location = new LocationBuilder("CAN").defaultLocation(true).build();
        var locations = Collections.singleton(location);
        var merchantName = "MerchantName";

        // when
        MerchantCommand command = new MerchantCommand(merchantName, type, locations);

        // then
        assertThat(command.type()).isEqualTo(type);
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
        MerchantCommand command = new MerchantCommand(merchantName, type, locations);

        // then
        assertThat(command.type()).isEqualTo(type);
    }

    @Test
    void whenCreateMerchantInvalidType_thenShouldThrowException() {

        // given
        var merchantName = "MerchantName";
        var locations = Collections.singleton(new LocationBuilder("CAN").defaultLocation(true).build());

        // when
        var exception = assertThrows(ConstraintViolationException.class, () -> new MerchantCommand(merchantName, null, locations));
        assertThat(exception).hasMessage("type: Type can't be null");
    }
}
