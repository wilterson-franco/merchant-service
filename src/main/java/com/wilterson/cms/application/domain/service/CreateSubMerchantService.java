package com.wilterson.cms.application.domain.service;

import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.application.port.in.MerchantCommand;
import com.wilterson.cms.common.StringGenerator;
import com.wilterson.cms.common.validation.Issue;
import com.wilterson.cms.common.validation.semantic.SemanticException;
import com.wilterson.cms.common.validation.semantic.SemanticValidatorFactory;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
class CreateSubMerchantService {

    private static final int GUID_LENGTH = 16;
    private SemanticValidatorFactory semanticValidatorFactory;
    private MerchantMapper merchantMapper;

    @Autowired
    void setSemanticValidatorFactory(SemanticValidatorFactory semanticValidatorFactory) {
        this.semanticValidatorFactory = semanticValidatorFactory;
    }

    @Autowired
    void setMerchantMapper(MerchantMapper merchantMapper) {
        this.merchantMapper = merchantMapper;
    }

    Merchant create(MerchantCommand merchantCommand) {

        var merchant = merchantMapper.toDomainEntity(merchantCommand, StringGenerator.generate(GUID_LENGTH));

        Set<Issue> issues = new HashSet<>();
        semanticValidatorFactory.subMerchantSemanticValidator().validate(merchant, issues);

        if (!CollectionUtils.isEmpty(issues)) {
            throw new SemanticException(issues);
        }

        return merchant;
    }
}
