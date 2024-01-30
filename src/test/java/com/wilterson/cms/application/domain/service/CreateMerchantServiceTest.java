package com.wilterson.cms.application.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wilterson.cms.application.domain.model.Location;
import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.application.domain.model.Merchant.MerchantBuilder;
import com.wilterson.cms.application.domain.model.MerchantType;
import com.wilterson.cms.application.port.in.MerchantCommand;
import jakarta.validation.ConstraintViolationException;
import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class CreateMerchantServiceTest {

    @Test
    void whenCreateMerchant_thenGuidShouldBeUnique() {

        // given
        var createMerchantService = new CreateMerchantService();
        var walmartCommand = new MerchantCommand("Walmart", MerchantType.MULTI_MERCHANT, Collections.emptySet());
        var homeDepotCommand = new MerchantCommand("The Home Depot", MerchantType.MULTI_MERCHANT, Collections.emptySet());

        // when
        var walmart = createMerchantService.create(walmartCommand);
        var homeDepot = createMerchantService.create(homeDepotCommand);

        // then
        assertThat(walmart.getGuid()).isNotEqualTo(homeDepot.getGuid());
    }

    @Disabled("Missing unique name semantic validation logic. I still need to work on the output port so this can be tested.")
    @Test
    void whenCreateMerchant_thenNameShouldBeUnique() {

        // given
        var createMerchantService = new CreateMerchantService();
        var walmartCommand1 = new MerchantCommand("Walmart", MerchantType.MULTI_MERCHANT, Collections.emptySet());
        var walmartCommand2 = new MerchantCommand("Walmart", MerchantType.MULTI_MERCHANT, Collections.emptySet());

        // when
        // then
        Merchant walmart1 = createMerchantService.create(walmartCommand1);
        var exception = assertThrows(IllegalArgumentException.class, () -> createMerchantService.create(walmartCommand2));
        assertThat(exception).hasMessage("Merchant name must be unique");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void whenCreateMerchant_thenNameIsMandatory(String name) {

        // given

        // then
        var violation = assertThrows(ConstraintViolationException.class, () -> new MerchantCommand(name, MerchantType.MULTI_MERCHANT, Collections.emptySet()));
        assertThat(violation).hasMessage("name: {merchant.name.required}");
    }

    @Nested
    class LocationHandling {

        @Test
        void whenSingleOrSubMerchantWithoutDefaultLocation_thenShouldThrowException() {

            // given
            var createMerchantService = new CreateMerchantService();
            var command = new MerchantCommand("MerchantName", MerchantType.SUB_MERCHANT, Collections.singleton(new Location(false)));

            // when
            // then
            var exception = assertThrows(IllegalArgumentException.class, () -> createMerchantService.create(command));
            assertThat(exception).hasMessage("Default location is required");
        }

        @Test
        void whenSingleOrSubMerchantWithoutMultipleDefaultLocation_thenShouldThrowException() {

            // given
            var createMerchantService = new CreateMerchantService();
            var command = new MerchantCommand("MerchantName", MerchantType.SUB_MERCHANT, List.of(new Location(true), new Location(true)));

            // when
            // then
            var exception = assertThrows(IllegalArgumentException.class, () -> createMerchantService.create(command));
            assertThat(exception).hasMessage("Only one location must be set as default");
        }

        @Test
        void whenSingleOrSubMerchantWithoutAnyLocation_thenShouldThrowException() {

            // given
            var createMerchantService = new CreateMerchantService();
            var command = new MerchantCommand("MerchantName", MerchantType.SUB_MERCHANT, Collections.emptySet());

            // when
            // then
            var exception = assertThrows(IllegalArgumentException.class, () -> createMerchantService.create(command));
            assertThat(exception).hasMessage("Default location is required");
        }

        @Test
        @DisplayName("when parent merchant without location " +
                "then MerchantCommand creation should succeed")
        void whenParentMerchantWithoutAnyLocation_thenCreateShouldSucceed() {

            // given
            var createMerchantService = new CreateMerchantService();
            var command = new MerchantCommand("MerchantName", MerchantType.MULTI_MERCHANT, Collections.emptySet());

            // when
            var merchant = createMerchantService.create(command);

            // then
            assertThat(merchant.getType()).isEqualTo(command.type());
        }

        @ParameterizedTest
        @EnumSource(value = MerchantType.class, names = {"PARTNER", "MULTI_MERCHANT"})
        void whenParentMerchantWithLocation_thenCreateShouldThrowException(MerchantType type) {

            // given
            var createMerchantService = new CreateMerchantService();
            var command = new MerchantCommand("MerchantName", type, Collections.singleton(new Location(false)));

            // when
            // then
            var exception = assertThrows(IllegalArgumentException.class, () -> createMerchantService.create(command));
            assertThat(exception).hasMessage("Parent merchant can't have location.");
        }
    }
}
