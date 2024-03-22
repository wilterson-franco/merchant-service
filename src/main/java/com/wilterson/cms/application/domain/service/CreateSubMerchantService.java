/*
 * Copyright 2024 Wilterson Franco
 */

package com.wilterson.cms.application.domain.service;

import com.wilterson.cms.application.domain.model.SubMerchant;
import com.wilterson.cms.application.port.in.SubMerchantCommand;
import com.wilterson.cms.common.StringGenerator;
import com.wilterson.cms.common.validation.SyntacticValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class CreateSubMerchantService {

    private static final int GUID_LENGTH = 16;
    private MerchantMapper merchantMapper;
    private SyntacticValidator syntacticValidator;

    @Autowired
    final void setMerchantMapper(MerchantMapper merchantMapper) {
        this.merchantMapper = merchantMapper;
    }

    @Autowired
    public void setSyntacticValidator(SyntacticValidator syntacticValidator) {
        this.syntacticValidator = syntacticValidator;
    }

    SubMerchant createSubMerchant(SubMerchantCommand subMerchantCommand) {

        syntacticValidator.validate(subMerchantCommand);

        return merchantMapper.toDomainEntity(subMerchantCommand, StringGenerator.generate(GUID_LENGTH));
    }
}
