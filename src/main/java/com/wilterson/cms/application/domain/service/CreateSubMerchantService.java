/*
 * Copyright 2024 Wilterson Franco
 */

package com.wilterson.cms.application.domain.service;

import com.wilterson.cms.application.domain.model.SubMerchant;
import com.wilterson.cms.application.port.in.SubMerchantCommand;
import com.wilterson.cms.common.StringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class CreateSubMerchantService {

    private static final int GUID_LENGTH = 16;
    private MerchantMapper merchantMapper;

    @Autowired
    final void setMerchantMapper(MerchantMapper merchantMapper) {
        this.merchantMapper = merchantMapper;
    }

    SubMerchant createSubMerchant(SubMerchantCommand subMerchantCommand) {
        return merchantMapper.toDomainEntity(subMerchantCommand, StringGenerator.generate(GUID_LENGTH));
    }
}
