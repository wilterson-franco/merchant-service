package com.wilterson.cms.application.port.in;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wilterson.cms.application.domain.model.Location;
import com.wilterson.cms.application.domain.model.MerchantType;
import java.util.Collections;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class MerchantCommandTest {

    @Nested
    class MerchantHandling {

        @Test
        void whenCreateHomeDepot_thenNameShouldBeHomeDepot() {

            // given
            Location location = new Location(true);
            String theHomeDepot = "The Home Depot";
            MerchantType merchantType = MerchantType.SINGLE_MERCHANT;
            Set<Location> locations = Collections.singleton(location);

            // when
            MerchantCommand command = new MerchantCommand(theHomeDepot, merchantType, locations);

            // then
            assertThat(command.name()).isEqualTo("The Home Depot");
        }

        @Test
        void whenBlankName_thenItShouldThrowException() {

            // given
            Location location = new Location(true);
            String emptyName = "      ";
            MerchantType merchantType = MerchantType.SINGLE_MERCHANT;
            Set<Location> locations = Collections.singleton(location);

            // when
            // then
            assertThrows(IllegalArgumentException.class, () -> new MerchantCommand(emptyName, merchantType, locations));
        }

        @DisplayName("given the merchant type " +
                "when non-parent merchants are created " +
                "then the type should be set accordingly")
        @ParameterizedTest
        @EnumSource(value = MerchantType.class, names = {"SINGLE_MERCHANT", "SUB_MERCHANT"})
        void whenNonParentMerchant_thenTypeShouldBeSetProperly(MerchantType type) {

            // given
            Location location = new Location(true);
            String merchantName = "MerchantName";
            Set<Location> locations = Collections.singleton(location);

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
            String merchantName = "MerchantName";
            Set<Location> locations = Collections.emptySet();

            // when
            MerchantCommand command = new MerchantCommand(merchantName, type, locations);

            // then
            assertThat(command.type()).isEqualTo(type);
        }
    }

    @Nested
    class LocationHandling {

        @Test
        void whenSingleOrSubMerchantWithoutDefaultLocation_thenShouldThrowException() {

            // given
            Set<Location> locations = Collections.singleton(new Location(false));
            String merchantName = "MerchantName";
            MerchantType merchantType = MerchantType.SUB_MERCHANT;

            // when
            // then
            assertThrows(IllegalArgumentException.class, () -> new MerchantCommand(merchantName, merchantType, locations));
        }

        @Test
        void whenSingleOrSubMerchantWithoutLocation_thenShouldThrowException() {

            // given
            String merchantName = "MerchantName";
            MerchantType merchantType = MerchantType.SUB_MERCHANT;
            Set<Location> locations = Collections.emptySet();

            // when
            // then
            assertThrows(IllegalArgumentException.class, () -> new MerchantCommand(merchantName, merchantType, locations));
        }

        @Test
        @DisplayName("when parent merchant without location " +
                "then MerchantCommand creation should succeed")
        void whenParentMerchantWithoutLocation_thenCreateShouldSucceed() {

            // given
            String merchantName = "MerchantName";
            MerchantType merchantType = MerchantType.MULTI_MERCHANT;
            Set<Location> locations = Collections.emptySet();

            // when
            MerchantCommand command = new MerchantCommand(merchantName, merchantType, locations);

            // then
            assertThat(command.type()).isEqualTo(merchantType);
        }

        @Test
        void whenParentMerchantWithLocation_thenCreateShouldThrowException() {

            // given
            Location location = new Location(false);
            String merchantName = "MerchantName";
            MerchantType merchantType = MerchantType.MULTI_MERCHANT;
            Set<Location> locations = Collections.singleton(location);

            // when
            // then
            assertThrows(IllegalArgumentException.class, () -> new MerchantCommand(merchantName, merchantType, locations));
        }
    }
}
