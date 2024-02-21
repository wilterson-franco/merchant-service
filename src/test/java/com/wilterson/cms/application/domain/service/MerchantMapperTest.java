package com.wilterson.cms.application.domain.service;

import static com.wilterson.cms.application.domain.model.MerchantType.MULTI_MERCHANT;
import static com.wilterson.cms.assertJ.LocationAssert.assertThat;
import static com.wilterson.cms.assertJ.MerchantAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

import com.wilterson.cms.application.domain.model.Location;
import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.application.port.in.LocationCommand;
import com.wilterson.cms.application.port.in.MerchantCommand;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class MerchantMapperTest {

    @Test
    void mapToDomainEntity() {

        // given
        var locationCommand = new LocationCommand("CAN", true);
        var merchantCommand = new MerchantCommand("MERCHANT-NAME", MULTI_MERCHANT, Collections.singleton(locationCommand));
        var mapper = new MerchantMapper(new LocationMapper());

        // when
        Merchant merchant = mapper.toDomainEntity(merchantCommand, "GUID");

        // then merchant
        assertThat(merchant).isNotNull();
        assertThat(merchant).nameEqualsTo("MERCHANT-NAME");
        assertThat(merchant).typeEqualsTo(MULTI_MERCHANT);
        assertThat(merchant).guidEqualsTo("GUID");
        assertThat(merchant).hasLocationsSize(1);
        assertThat(merchant).hasDefaultLocation();
        assertThat(merchant).containsLocationByCountryCodeIgnoreCase("CAN");

        // and then location
        Optional<Location> optional = merchant.getLocations().stream().filter(l -> "CAN".equalsIgnoreCase(l.getCountryCode())).findFirst();
        assertThat(optional).isPresent();
        Location location = optional.get();
        assertThat(location).countryCodeEqualsTo("CAN");
        assertThat(location).isDefault();
    }
}
