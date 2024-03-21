/*
 * Copyright 2024 Wilterson Franco
 */

package com.wilterson.cms.application.domain.service;

import com.wilterson.cms.application.domain.model.SubMerchant;
import com.wilterson.cms.application.port.in.CreateSubMerchantUseCase;
import com.wilterson.cms.application.port.in.SubMerchantCommand;
import com.wilterson.cms.common.validation.SyntacticValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService implements CreateSubMerchantUseCase {

    private SyntacticValidator syntacticValidator;
    private CreateSubMerchantService createSubMerchantService;

    @Autowired
    public void setCreateSubMerchantService(CreateSubMerchantService createSubMerchantService) {
        this.createSubMerchantService = createSubMerchantService;
    }

    @Autowired
    public void setSyntacticValidator(SyntacticValidator syntacticValidator) {
        this.syntacticValidator = syntacticValidator;
    }

    @Override
    public SubMerchant createSubMerchant(SubMerchantCommand subMerchantCommand) {

        syntacticValidator.validate(subMerchantCommand);

        return createSubMerchantService.createSubMerchant(subMerchantCommand);
    }
}
