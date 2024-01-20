package com.wilterson.cms.application.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.application.domain.model.MerchantType;
import com.wilterson.cms.application.port.in.MerchantCommand;
import java.util.Collections;
import org.junit.jupiter.api.Test;

public class CreateMerchantServiceTest {

    @Test
    void whenCreateMerchant_thenGuidShouldBeUnique() {

        // given
        CreateMerchantService createMerchantService = new CreateMerchantService();
        MerchantCommand walmartCommand = new MerchantCommand("Walmart", MerchantType.MULTI_MERCHANT, Collections.emptySet());
        MerchantCommand homeDepotCommand = new MerchantCommand("The Home Depot", MerchantType.MULTI_MERCHANT, Collections.emptySet());

        // when
        Merchant walmart = createMerchantService.create(walmartCommand);
        Merchant homeDepot = createMerchantService.create(homeDepotCommand);

        // then
        assertThat(walmart.getGuid()).isNotEqualTo(homeDepot.getGuid());
    }
}
