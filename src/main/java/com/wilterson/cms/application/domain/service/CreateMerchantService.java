package com.wilterson.cms.application.domain.service;

import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.application.port.in.CreateMerchantUseCase;
import com.wilterson.cms.application.port.in.MerchantCommand;

class CreateMerchantService implements CreateMerchantUseCase {

    @Override
    public Merchant create(MerchantCommand command) {
        return new Merchant(command.name(), command.type(), command.locations());
    }
}
