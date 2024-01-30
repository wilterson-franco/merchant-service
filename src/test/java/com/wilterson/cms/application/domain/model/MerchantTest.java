package com.wilterson.cms.application.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wilterson.cms.application.domain.model.Merchant.MerchantBuilder;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

class MerchantTest {

    @Test
    void whenBuildMerchant_thenValidationIsMandatory() {

        var violation = assertThrows(ConstraintViolationException.class, () -> new MerchantBuilder("MerchantName", MerchantType.SUB_MERCHANT, null).build());
        assertThat(violation).hasMessage("validator: must not be null");
    }
}