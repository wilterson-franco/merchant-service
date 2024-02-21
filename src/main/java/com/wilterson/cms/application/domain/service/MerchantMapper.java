package com.wilterson.cms.application.domain.service;

import com.wilterson.cms.application.domain.model.Location;
import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.application.domain.model.Merchant.MerchantBuilder;
import com.wilterson.cms.application.port.in.LocationCommand;
import com.wilterson.cms.application.port.in.MerchantCommand;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class MerchantMapper {

    private final LocationMapper locationMapper;

    public Merchant toDomainEntity(MerchantCommand command, String guid) {

        return new MerchantBuilder(command.name(), guid, command.type())
                .locations(toLocationDomainEntities(command.locationCommands()))
                .build();
    }

    private Set<Location> toLocationDomainEntities(Set<LocationCommand> locationCommands) {
        return locationCommands
                .stream()
                .map(locationMapper::toDomainEntity)
                .collect(Collectors.toSet());
    }
}
