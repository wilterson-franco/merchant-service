package com.wilterson.cms.application.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MerchantTest {

    @Test
    void whenMerchantCreated_thenGuidIsNotNull() {

        // given

        // when
        Merchant merchant = Merchant.builder("Walmart").build();

        // then
        assertThat(merchant.guid()).isNotNull();
    }

    @Test
    void whenMultipleMerchantsCreated_thenGuidIsUnique() {

        // given

        // when
        Merchant walmart = Merchant.builder("Walmart").build();
        Merchant theHomeDepot = Merchant.builder("The Home Depot").build();

        // then
        assertThat(walmart.guid()).isNotEqualTo(theHomeDepot.guid());
    }
}
