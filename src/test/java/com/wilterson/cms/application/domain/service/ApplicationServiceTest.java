package com.wilterson.cms.application.domain.service;

import static com.wilterson.cms.application.port.in.MerchantTypeCommand.MULTI_MERCHANT;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.wilterson.cms.application.domain.model.MerchantType;
import com.wilterson.cms.application.port.in.MerchantCommand;
import com.wilterson.cms.application.port.in.MerchantTypeCommand;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @Mock
    private CreateSubMerchantService createSubMerchantService;

    @InjectMocks
    private ApplicationService applicationService;

    @Test
    void whenCreateSubMerchant_thenServiceCreateAndValidatorShouldBeCalledOnce() {

        // given
        var command = new MerchantCommand("MerchantName", MULTI_MERCHANT, Collections.emptySet());

        // when
        applicationService.create(command);

        // then
        verify(createSubMerchantService, times(1)).create(command);
    }
}
