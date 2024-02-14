package com.wilterson.cms.application.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.application.domain.model.MerchantType;
import com.wilterson.cms.application.port.in.MerchantCommand;
import com.wilterson.cms.common.cache.CacheManager;
import com.wilterson.cms.common.cache.CachedEntity;
import com.wilterson.cms.common.validation.SemanticValidator;
import com.wilterson.cms.common.validation.SemanticValidatorFactory;
import com.wilterson.cms.common.validation.UniqueGuidException;
import com.wilterson.cms.common.validation.UniqueNameException;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SemanticValidatorFactoryTest {

    @Mock
    private Merchant merchant;

    @Mock
    private CacheManager cacheManager;

    @InjectMocks
    SemanticValidatorFactory semanticValidatorFactory;

    @Test
    void whenDuplicatedGuid_thenCreateMerchantShouldFail() {

        // given
        MerchantCommand command = new MerchantCommand("MerchantName", MerchantType.MULTI_MERCHANT, Collections.emptySet());
        SemanticValidator<Merchant> validator = semanticValidatorFactory.validator(command);
        Optional<CachedEntity> cachedEntity = Optional.of(new CachedEntity("MerchantName", "AAAAAA"));

        // when
        doReturn("AAAAAA").when(merchant).getGuid();
        doReturn(cachedEntity).when(cacheManager).getEntityByGuid("AAAAAA");

        // then
        var exception = assertThrows(UniqueGuidException.class, () -> validator.validate(merchant));
        assertThat(exception).hasMessage("Merchant GUID must be unique");
    }

    @Test
    void whenDuplicateName_thenCreateMerchantShouldFail() {

        // given
        MerchantCommand command = new MerchantCommand("MerchantName", MerchantType.MULTI_MERCHANT, Collections.emptySet());
        SemanticValidator<Merchant> validator = semanticValidatorFactory.validator(command);
        Optional<CachedEntity> cachedEntity = Optional.of(new CachedEntity("MerchantName", "AAAAAA"));

        // when
        doReturn("MerchantName").when(merchant).getName();
        doReturn(cachedEntity).when(cacheManager).getEntityByName("MerchantName");

        // then
        var exception = assertThrows(UniqueNameException.class, () -> validator.validate(merchant));
        assertThat(exception).hasMessage("Merchant name must be unique");
    }
}
