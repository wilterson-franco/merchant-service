package com.wilterson.cms.application.port.in;

import com.wilterson.cms.application.domain.model.MultiMerchant;

@FunctionalInterface
public interface CreateMultiMerchantUseCase {

    MultiMerchant executeCommand(MultiMerchantCommand command);
}
