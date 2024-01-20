package com.wilterson.cms.application.domain.model;

import com.wilterson.cms.common.StringGenerator;
import lombok.Builder;

@Builder(builderMethodName = "hiddenBuilder")
public record Merchant(String name, String guid, MerchantType type) {

    public static MerchantBuilder builder(String name) {
        return hiddenBuilder()
                .name(name)
                .guid(StringGenerator.generate(16));
    }
}
