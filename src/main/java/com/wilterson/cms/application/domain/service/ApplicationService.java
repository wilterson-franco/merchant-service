/*
 * Copyright 2024 Wilterson Franco
 */

package com.wilterson.cms.application.domain.service;

import com.wilterson.cms.application.domain.model.MultiMerchant;
import com.wilterson.cms.application.domain.model.SubMerchant;
import com.wilterson.cms.application.port.in.CreateMultiMerchantUseCase;
import com.wilterson.cms.application.port.in.CreateSubMerchantUseCase;
import com.wilterson.cms.application.port.in.MultiMerchantCommand;
import com.wilterson.cms.application.port.in.SubMerchantCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService implements CreateSubMerchantUseCase, CreateMultiMerchantUseCase {

    private CreateSubMerchantService createSubMerchantService;

    @Autowired
    public void setCreateSubMerchantService(CreateSubMerchantService createSubMerchantService) {
        this.createSubMerchantService = createSubMerchantService;
    }

    @Override
    public SubMerchant executeCommand(SubMerchantCommand subMerchantCommand) {

        return createSubMerchantService.createSubMerchant(subMerchantCommand);
    }

    @Override
    public MultiMerchant executeCommand(MultiMerchantCommand command) {
        return null;
    }
}
