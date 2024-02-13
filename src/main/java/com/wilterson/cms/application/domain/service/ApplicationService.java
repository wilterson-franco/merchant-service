package com.wilterson.cms.application.domain.service;

import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.application.port.in.CreateMerchantUseCase;
import com.wilterson.cms.application.port.in.MerchantCommand;
import com.wilterson.cms.common.validation.SemanticValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService implements CreateMerchantUseCase {

    private final SemanticValidatorFactory semanticValidatorFactory;
    private final CreateMerchantService createMerchantService;

    @Override
    public Merchant create(MerchantCommand command) {
        try {
            return createMerchantService.create(command, semanticValidatorFactory.validator(command));
        } catch (IllegalArgumentException exception) {

            // TODO: catch the specialized exception, convert it into an application exception and rethrow it
            throw ApplicationException.builder()
                    .source("Merchant Service")
                    .reasonCode("some reason code")
                    .description(exception.getMessage())
                    .recoverable(false)
                    .build();
        }
    }
}
