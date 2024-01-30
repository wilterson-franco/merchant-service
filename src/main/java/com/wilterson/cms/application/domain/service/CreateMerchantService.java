package com.wilterson.cms.application.domain.service;

import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.application.domain.model.Merchant.MerchantBuilder;
import com.wilterson.cms.application.port.in.CreateMerchantUseCase;
import com.wilterson.cms.application.port.in.MerchantCommand;

class CreateMerchantService implements CreateMerchantUseCase {

    @Override
    public Merchant create(MerchantCommand command) {
        return new MerchantBuilder(command.name(), command.type(), SemanticValidatorFactory.validator(command))
                .locations(command.locations())
                .build();
    }
}
