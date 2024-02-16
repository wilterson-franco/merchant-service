package com.wilterson.cms.application.port.in;

import com.wilterson.cms.application.domain.model.Merchant;

@FunctionalInterface
public interface CreateSubMerchantUseCase {

    Merchant create(MerchantCommand command);
}
