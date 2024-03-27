package com.wilterson.cms.adapter.in.web;

import java.util.List;

public record SubMerchantDto(
        String name,
        List<LocationDto> locations) {

}
