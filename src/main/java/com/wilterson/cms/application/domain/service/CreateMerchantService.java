package com.wilterson.cms.application.domain.service;

import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.application.domain.model.Merchant.MerchantBuilder;
import com.wilterson.cms.application.port.in.MerchantCommand;
import com.wilterson.cms.application.port.in.SemanticValidatorUseCase;

abstract class CreateMerchantService {

    public static Merchant create(MerchantCommand command, SemanticValidatorUseCase<Merchant> semanticValidator) {
        return new MerchantBuilder(command.name(), command.type(), semanticValidator)
                .locations(command.locations())
                .build();
    }
}
