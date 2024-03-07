package com.wilterson.cms.application.port.in;

import com.wilterson.cms.application.domain.model.SubMerchant;

@FunctionalInterface
public interface CreateSubMerchantUseCase {

    SubMerchant create(MerchantCommand command);
}
