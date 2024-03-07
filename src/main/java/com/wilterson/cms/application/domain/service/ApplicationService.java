package com.wilterson.cms.application.domain.service;

import com.wilterson.cms.application.domain.model.SubMerchant;
import com.wilterson.cms.application.port.in.CreateSubMerchantUseCase;
import com.wilterson.cms.application.port.in.MerchantCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService implements CreateSubMerchantUseCase {

    private final CreateSubMerchantService createSubMerchantService;

    @Override
    public SubMerchant create(MerchantCommand command) {
        return createSubMerchantService.create(command);
    }
}
