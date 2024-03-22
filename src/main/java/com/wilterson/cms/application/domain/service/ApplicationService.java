/*
 * Copyright 2024 Wilterson Franco
 */

package com.wilterson.cms.application.domain.service;

import com.wilterson.cms.application.domain.model.SubMerchant;
import com.wilterson.cms.application.port.in.CreateSubMerchantUseCase;
import com.wilterson.cms.application.port.in.SubMerchantCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService implements CreateSubMerchantUseCase {

    private CreateSubMerchantService createSubMerchantService;

    @Autowired
    public void setCreateSubMerchantService(CreateSubMerchantService createSubMerchantService) {
        this.createSubMerchantService = createSubMerchantService;
    }

    @Override
    public SubMerchant executeCommand(SubMerchantCommand subMerchantCommand) {

        return createSubMerchantService.createSubMerchant(subMerchantCommand);
    }
}
