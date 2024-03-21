/*
 * Copyright 2024 Wilterson Franco
 */

package com.wilterson.cms.application.domain.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wilterson.cms.application.port.in.CreateSubMerchantUseCase;
import com.wilterson.cms.application.port.in.SubMerchantCommand;
import jakarta.validation.ConstraintViolationException;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationServiceValidationTest {

    @Autowired
    private CreateSubMerchantUseCase createSubMerchantUseCase;

    @Test
    void whenCreateSubMerchant_thenValidationShouldKickIn() {

        // given
        var command = new SubMerchantCommand("", Collections.emptyList());

        // when & then
        assertThrows(ConstraintViolationException.class, () -> createSubMerchantUseCase.createSubMerchant(command));
    }
}
