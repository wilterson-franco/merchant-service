/*
 * Copyright 2024 Wilterson Franco
 */

package com.wilterson.cms.application.domain.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.wilterson.cms.application.port.in.SubMerchantCommand;
import com.wilterson.cms.common.validation.SyntacticValidator;
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

    @Mock
    private SyntacticValidator syntacticValidator;

    @InjectMocks
    private ApplicationService applicationService;

    @Test
    void whenCreateSubMerchant_thenServiceCreateAndValidatorShouldBeCalledOnce() {

        // given
        var command = new SubMerchantCommand("MERCHANT-NAME", Collections.emptyList());

        // when
        applicationService.createSubMerchant(command);

        // then
        verify(syntacticValidator, times(1)).validate(command);
        verify(createSubMerchantService, times(1)).createSubMerchant(command);
    }
}
