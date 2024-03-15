/*
 * Copyright 2024 Wilterson Franco
 */

package com.wilterson.cms.application.domain.service;

import com.wilterson.cms.application.domain.model.Location;
import com.wilterson.cms.application.domain.model.SubMerchant;
import com.wilterson.cms.application.domain.model.SubMerchant.SubMerchantBuilder;
import com.wilterson.cms.application.port.in.LocationCommand;
import com.wilterson.cms.application.port.in.SubMerchantCommand;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
class MerchantMapper {

    private final LocationMapper locationMapper;

    public MerchantMapper(LocationMapper locationMapper) {
        this.locationMapper = locationMapper;
    }

    SubMerchant toDomainEntity(SubMerchantCommand command, String guid) {

        Objects.requireNonNull(guid);

        return new SubMerchantBuilder(command.name(), guid)
                .locations(toLocationDomainEntities(command.locationCommands()))
                .build();
    }

    private List<Location> toLocationDomainEntities(List<LocationCommand> locationCommands) {
        return locationCommands
                .stream()
                .map(locationMapper::toDomainEntity)
                .collect(Collectors.toList());
    }
}
