package com.wilterson.cms.application.domain.service;

import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.application.domain.model.Merchant.MerchantBuilder;
import com.wilterson.cms.application.port.in.MerchantCommand;
import com.wilterson.cms.common.validation.SemanticValidator;
import org.springframework.stereotype.Service;

@Service
public class CreateMerchantService {

    public Merchant create(MerchantCommand command, SemanticValidator<Merchant> semanticValidator) {
        return new MerchantBuilder(command.name(), command.type(), semanticValidator)
                .locations(command.locations())
                .build();
    }
}
