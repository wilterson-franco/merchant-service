package com.wilterson.cms.adapter.in.web;

import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateSubMerchantController {

    @PostMapping(value = "/sub-merchants", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SubMerchantDto CreateSubMerchant(@RequestBody SubMerchantDto subMerchantDto) {
        return new SubMerchantDto("MERCHANT-NAME", Collections.emptyList());
    }
}
