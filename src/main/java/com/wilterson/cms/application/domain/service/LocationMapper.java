package com.wilterson.cms.application.domain.service;

import com.wilterson.cms.application.domain.model.Location;
import com.wilterson.cms.application.domain.model.Location.LocationBuilder;
import com.wilterson.cms.application.port.in.LocationCommand;
import org.springframework.stereotype.Service;

@Service
class LocationMapper {

    public Location toDomainEntity(LocationCommand command) {
        return new LocationBuilder(command.countryCode())
                .defaultLocation(command.isDefault())
                .build();
    }
}
