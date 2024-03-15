/*
 * Copyright 2024 Wilterson Franco
 */

package com.wilterson.cms.application.domain.service;

import static com.wilterson.cms.assertJ.LocationAssert.assertThat;
import static com.wilterson.cms.assertJ.MerchantAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

import com.wilterson.cms.application.domain.model.Location;
import com.wilterson.cms.application.domain.model.SubMerchant;
import com.wilterson.cms.application.port.in.LocationCommand;
import com.wilterson.cms.application.port.in.SubMerchantCommand;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class SubMerchantMapperTest {

    @Test
    void mapToDomainEntity() {

        // given
        var locationCommand = new LocationCommand("CAN", true);
        var subMerchantCommand = new SubMerchantCommand("MERCHANT-NAME", Collections.singletonList(locationCommand));
        var mapper = new MerchantMapper(new LocationMapper());

        // when
        SubMerchant subMerchant = mapper.toDomainEntity(subMerchantCommand, "GUID");

        // then merchant
        assertThat(subMerchant).isNotNull();
        assertThat(subMerchant).nameEqualsTo("MERCHANT-NAME");
        assertThat(subMerchant).guidEqualsTo("GUID");
        assertThat(subMerchant).hasLocationsSize(1);
        assertThat(subMerchant).hasDefaultLocation();
        assertThat(subMerchant).containsLocationByCountryCodeIgnoreCase("CAN");

        // and then location
        Optional<Location> optional = subMerchant.getLocations().stream().filter(l -> "CAN".equalsIgnoreCase(l.getCountryCode())).findFirst();
        assertThat(optional).isPresent();
        Location location = optional.get();
        assertThat(location).countryCodeEqualsTo("CAN");
        assertThat(location).isDefault();
    }
}
