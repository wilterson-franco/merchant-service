package com.wilterson.cms.application.port.in;

import com.wilterson.cms.application.domain.model.Merchant;

public interface CreateMerchantUseCase {

    Merchant create(MerchantCommand command);
}
