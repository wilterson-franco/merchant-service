/*
 * Copyright 2024 Wilterson Franco
 */

package com.wilterson.cms.application.domain.service;

import com.wilterson.cms.application.domain.model.SubMerchant;
import com.wilterson.cms.application.port.in.MultiMerchantCommand;
import com.wilterson.cms.common.validation.SyntacticValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class CreateMultiMerchantService {

    private SyntacticValidator syntacticValidator;

    @Autowired
    public void setSyntacticValidator(SyntacticValidator syntacticValidator) {
        this.syntacticValidator = syntacticValidator;
    }

    SubMerchant createMultiMerchant(MultiMerchantCommand multiMerchantCommand) {

        syntacticValidator.validate(multiMerchantCommand);

        return null;
    }
}
