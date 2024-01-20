package com.wilterson.cms.application.domain.model;

import com.wilterson.cms.common.StringGenerator;
import com.wilterson.cms.common.validation.Validatable;
import java.util.Collection;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Merchant implements Validatable {

    private static final int GUID_LENGTH = 16;

    private final String guid = StringGenerator.generate(GUID_LENGTH);
    private final String name;
    private final MerchantType type;
    private final Collection<Location> locations;
}
