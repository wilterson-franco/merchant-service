package com.wilterson.cms.application.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.wilterson.cms.application.domain.model.MerchantType;
import com.wilterson.cms.application.port.in.MerchantCommand;
import com.wilterson.cms.common.validation.SemanticValidatorFactory;
import java.util.Collections;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContextException;

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
        var illegalArgumentException = new IllegalArgumentException("Merchant name must be unique");

        // when
        doThrow(illegalArgumentException).when(semanticValidatorFactory).validator(command);

        // then
        var applicationException = assertThrows(ApplicationException.class, () -> applicationService.create(command));
        assertThat(applicationException).hasMessage("Merchant name must be unique");
    }
}
