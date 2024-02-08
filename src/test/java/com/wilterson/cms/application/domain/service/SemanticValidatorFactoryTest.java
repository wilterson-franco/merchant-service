package com.wilterson.cms.application.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.application.domain.model.MerchantType;
import com.wilterson.cms.application.port.in.MerchantCommand;
import com.wilterson.cms.application.port.in.SemanticValidatorUseCase;
import com.wilterson.cms.common.validation.SemanticValidatorFactory;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SemanticValidatorFactoryTest {

    @Mock
    private Merchant merchant;

    @Test
    void whenDuplicatedGuid_thenCreateMerchantShouldFail() {

        // given
        MerchantCommand merchantCommand = new MerchantCommand("MerchantName", MerchantType.MULTI_MERCHANT, Collections.emptySet());
        SemanticValidatorUseCase<Merchant> validator = SemanticValidatorFactory.validator(merchantCommand);

        // when
        // then
        doReturn("ABCDEFGSW").when(merchant).getGuid();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(merchant));
        assertThat(exception).hasMessage("Merchant GUID must be unique");
    }

}
