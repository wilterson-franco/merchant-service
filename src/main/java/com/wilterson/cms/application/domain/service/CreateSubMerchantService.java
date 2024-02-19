package com.wilterson.cms.application.domain.service;

import com.wilterson.cms.application.domain.model.Location;
import com.wilterson.cms.application.domain.model.Location.LocationBuilder;
import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.application.domain.model.Merchant.MerchantBuilder;
import com.wilterson.cms.application.port.in.MerchantCommand;
import com.wilterson.cms.common.StringGenerator;
import com.wilterson.cms.common.validation.Issue;
import com.wilterson.cms.common.validation.SemanticException;
import com.wilterson.cms.common.validation.SemanticValidatorFactory;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class CreateSubMerchantService {

    private static final int GUID_LENGTH = 16;
    private final SemanticValidatorFactory semanticValidatorFactory;

    public Merchant create(MerchantCommand merchantCommand) {

        var guid = StringGenerator.generate(GUID_LENGTH);

        Set<Location> locations = merchantCommand.locationCommands()
                .stream()
                .map(c -> new LocationBuilder(c.countryCode()).defaultLocation(c.isDefault()).build())
                .collect(Collectors.toSet());

        var merchant = new MerchantBuilder(merchantCommand.name(), guid, merchantCommand.type())
                .locations(locations)
                .build();

        Set<Issue> issues = new HashSet<>();
        semanticValidatorFactory.subMerchantSemanticValidator().validate(merchant, issues);

        if (!CollectionUtils.isEmpty(issues)) {
            throw new SemanticException(issues);
        }

        return merchant;
    }
}
