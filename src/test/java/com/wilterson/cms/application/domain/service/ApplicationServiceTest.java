package com.wilterson.cms.application.domain.service;

import static com.wilterson.cms.assertJ.CmsAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.wilterson.cms.application.domain.model.MerchantType;
import com.wilterson.cms.application.port.in.MerchantCommand;
import com.wilterson.cms.common.validation.SemanticValidationException;
import com.wilterson.cms.common.validation.SemanticValidatorFactory;
import com.wilterson.cms.common.validation.UniqueNameException;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @Mock
    private SemanticValidatorFactory semanticValidatorFactory;

    @Mock
    private CreateMerchantService createMerchantService;

    @InjectMocks
    private ApplicationService applicationService;

    @Test
    void whenCreateMerchant_thenServiceCreateAndValidatorShouldBeCalledOnce() {

        // given
        var command = new MerchantCommand("MerchantName", MerchantType.MULTI_MERCHANT, Collections.emptySet());

        // when
        applicationService.create(command);

        // then
        verify(createMerchantService, times(1)).create(eq(command), any());
        verify(semanticValidatorFactory, times(1)).validator(command);
    }

    @Test
    void whenDuplicateName_thenApplicationException() {

        // given
        var command = new MerchantCommand("MerchantName", MerchantType.MULTI_MERCHANT, Collections.emptySet());
        var validationException = new UniqueNameException("Merchant name must be unique");

        // when
        doThrow(validationException).when(semanticValidatorFactory).validator(command);

        // then
        var applicationException = assertThrows(ApplicationException.class, () -> applicationService.create(command));
        assertThat(applicationException).hasDescription("Merchant name must be unique");
        assertThat(applicationException).hasSource("Merchant Service");
        assertThat(applicationException).hasReasonCode("some reason code");
        assertThat(applicationException).isNotRecoverable();
    }
}
