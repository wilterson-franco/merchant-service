package com.wilterson.cms.application.domain.service;

import com.wilterson.cms.application.domain.model.SubMerchant;
import com.wilterson.cms.application.port.in.MerchantCommand;
import com.wilterson.cms.common.StringGenerator;
import com.wilterson.cms.common.validation.Issue;
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
class CreateSubMerchantService {

    private static final int GUID_LENGTH = 16;
    private MerchantMapper merchantMapper;

    @Autowired
    void setMerchantMapper(MerchantMapper merchantMapper) {
        this.merchantMapper = merchantMapper;
    }

    SubMerchant create(MerchantCommand merchantCommand) {

        var merchant = merchantMapper.toDomainEntity(merchantCommand, StringGenerator.generate(GUID_LENGTH));

        Set<Issue> issues = new HashSet<>();

        if (!CollectionUtils.isEmpty(issues)) {
//            throw new SemanticException(issues);
        }

        return merchant;
    }
}
