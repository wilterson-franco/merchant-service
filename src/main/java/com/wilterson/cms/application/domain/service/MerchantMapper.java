package com.wilterson.cms.application.domain.service;

import com.wilterson.cms.application.domain.model.Location;
import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.application.domain.model.Merchant.MerchantBuilder;
import com.wilterson.cms.application.domain.model.MerchantType;
import com.wilterson.cms.application.port.in.LocationCommand;
import com.wilterson.cms.application.port.in.MerchantCommand;
import com.wilterson.cms.application.port.in.MerchantTypeCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
class MerchantMapper {

    private final LocationMapper locationMapper;
    private final MerchantTypeMapper merchantTypeMapper;

    @Validated
    Merchant toDomainEntity(@Valid MerchantCommand command, @NotBlank String guid) {

        return new MerchantBuilder(command.name(), guid, toMerchantTypeDomainEntity(command.type()))
                .locations(toLocationDomainEntities(command.locationCommands()))
                .build();
    }

    private List<Location> toLocationDomainEntities(List<LocationCommand> locationCommands) {
        return locationCommands
                .stream()
                .map(locationMapper::toDomainEntity)
                .collect(Collectors.toList());
    }

    private MerchantType toMerchantTypeDomainEntity(MerchantTypeCommand merchantTypeCommand) {
        return merchantTypeMapper.toDomainEntity(merchantTypeCommand);
    }
}
