package com.wilterson.cms.application.domain.service;

import com.wilterson.cms.application.domain.model.MerchantType;
import com.wilterson.cms.application.port.in.MerchantTypeCommand;
import org.springframework.stereotype.Service;

@Service
class MerchantTypeMapper {

    MerchantType toDomainEntity(MerchantTypeCommand command) {
        return MerchantType.valueOf(command.name());
    }
}
