/*
 * Copyright 2024 Wilterson Franco
 */

package com.wilterson.cms.application.port.in;

import com.wilterson.cms.application.domain.model.SubMerchant;

@FunctionalInterface
public interface CreateSubMerchantUseCase {

    SubMerchant createSubMerchant(SubMerchantCommand command);
}
