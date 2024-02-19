package com.wilterson.cms.application.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wilterson.cms.application.domain.model.Location.LocationBuilder;
import com.wilterson.cms.application.domain.model.Merchant.MerchantBuilder;
import jakarta.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class MerchantTest {

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
        assertThrows(ConstraintViolationException.class, () -> new MerchantBuilder(emptyName, "GUID", merchantType).locations(locations).build());
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
    void whenCreateMerchantInvalidType_thenShouldThrowException() {

        // given
        var merchantName = "MerchantName";
        var locations = Collections.singleton(new LocationBuilder("CAN").defaultLocation(true).build());

        // when
        var exception = assertThrows(ConstraintViolationException.class, () -> new MerchantBuilder(merchantName, "GUID", null)
                .locations(locations)
                .build());

        assertThat(exception).hasMessage("type: must not be null");
    }
}