/*
 * Copyright 2024 Wilterson Franco
 */

package com.wilterson.cms.application.port.in;

import com.wilterson.cms.application.domain.model.SubMerchant;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
@FunctionalInterface
public interface CreateSubMerchantUseCase {

    SubMerchant createSubMerchant(@Valid SubMerchantCommand command);
}
