package com.wilterson.cms.application.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

import com.wilterson.cms.application.domain.model.Location;
import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.application.domain.model.MerchantType;
import com.wilterson.cms.application.port.in.MerchantCommand;
import com.wilterson.cms.assertJ.MerchantAssert;
import com.wilterson.cms.assertJ.CmsAssertions;
import com.wilterson.cms.common.cache.CacheManager;
import com.wilterson.cms.common.validation.SemanticValidator;
import com.wilterson.cms.common.validation.SemanticValidatorFactory;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateMerchantServiceTest {

    @Mock
    private CacheManager cacheManager;

    @Mock
    SemanticValidator<Merchant> validator;

    @InjectMocks
    private CreateMerchantService createMerchantService;

    @Test
    void whenUniqueGuid_thenCreateMerchantShouldSucceed() {

        // given
        var walmartCommand = new MerchantCommand("Walmart", MerchantType.MULTI_MERCHANT, Collections.emptySet());

        // when
        var walmart = createMerchantService.create(walmartCommand, validator);

        // then
        CmsAssertions.assertThat(walmart).isNotNull();
    }

    @Test
    void whenGuidDuplicated_thenCreateMerchantShouldThrowException() {

        // given
        var command = new MerchantCommand("MerchantNameA", MerchantType.MULTI_MERCHANT, Collections.emptySet());
        IllegalArgumentException exception = new IllegalArgumentException("Merchant GUID must be unique");

        // when
        doThrow(exception).when(validator).validate(any(Merchant.class));

        // then
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> createMerchantService.create(command, validator));
        assertThat(e).hasMessage("Merchant GUID must be unique");
    }

    @Test
    void whenNameDuplicated_thenCreateMerchantShouldThrowException() {

        // given
        var command = new MerchantCommand("MerchantNameA", MerchantType.MULTI_MERCHANT, Collections.emptySet());
        IllegalArgumentException exception = new IllegalArgumentException("Merchant name must be unique");

        // when
        doThrow(exception).when(validator).validate(any(Merchant.class));

        // then
        var e = assertThrows(IllegalArgumentException.class, () -> createMerchantService.create(command, validator));
        assertThat(e).hasMessage("Merchant name must be unique");
    }

    @Nested
    class LocationHandling {

        @Test
        void whenSingleOrSubMerchantWithoutDefaultLocation_thenShouldThrowException() {

            // given
            var command = new MerchantCommand("MerchantName", MerchantType.SUB_MERCHANT, Collections.singleton(new Location(false)));
            SemanticValidator<Merchant> validator = new SemanticValidatorFactory(cacheManager).validator(command);

            // when
            // then
            var exception = assertThrows(IllegalArgumentException.class, () -> createMerchantService.create(command, validator));
            assertThat(exception).hasMessage("Default location is required");
        }

        @Test
        void whenSingleOrSubMerchantWithoutMultipleDefaultLocation_thenShouldThrowException() {

            // given
            var command = new MerchantCommand("MerchantName", MerchantType.SUB_MERCHANT, List.of(new Location(true), new Location(true)));
            SemanticValidator<Merchant> validator = new SemanticValidatorFactory(cacheManager).validator(command);

            // when
            // then
            var exception = assertThrows(IllegalArgumentException.class, () -> createMerchantService.create(command, validator));
            assertThat(exception).hasMessage("Only one location must be set as default");
        }

        @Test
        void whenSingleOrSubMerchantWithoutAnyLocation_thenShouldThrowException() {

            // given
            var command = new MerchantCommand("MerchantName", MerchantType.SUB_MERCHANT, Collections.emptySet());
            SemanticValidator<Merchant> validator = new SemanticValidatorFactory(cacheManager).validator(command);

            // when
            // then
            var exception = assertThrows(IllegalArgumentException.class, () -> createMerchantService.create(command, validator));
            assertThat(exception).hasMessage("Default location is required");
        }

        @Test
        @DisplayName("when parent merchant without location " +
                "then MerchantCommand creation should succeed")
        void whenParentMerchantWithoutAnyLocation_thenCreateShouldSucceed() {

            // given
            var command = new MerchantCommand("MerchantName", MerchantType.MULTI_MERCHANT, Collections.emptySet());
            SemanticValidator<Merchant> validator = new SemanticValidatorFactory(cacheManager).validator(command);

            // when
            var merchant = createMerchantService.create(command, validator);

            // then
            MerchantAssert.assertThat(merchant).typeEqualsTo(command.type());
        }

        @ParameterizedTest
        @EnumSource(value = MerchantType.class, names = {"PARTNER", "MULTI_MERCHANT"})
        void whenParentMerchantWithLocation_thenCreateShouldThrowException(MerchantType type) {

            // given
            var command = new MerchantCommand("MerchantName", type, Collections.singleton(new Location(false)));
            SemanticValidator<Merchant> validator = new SemanticValidatorFactory(cacheManager).validator(command);

            // when
            // then
            var exception = assertThrows(IllegalArgumentException.class, () -> createMerchantService.create(command, validator));
            assertThat(exception).hasMessage("Parent merchant can't have location.");
        }
    }
}
