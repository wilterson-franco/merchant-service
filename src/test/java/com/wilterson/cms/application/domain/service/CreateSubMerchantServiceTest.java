package com.wilterson.cms.application.domain.service;

import static com.wilterson.cms.application.port.in.MerchantTypeCommand.MULTI_MERCHANT;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.wilterson.cms.application.port.in.MerchantCommand;
import com.wilterson.cms.common.cache.CacheManager;
import com.wilterson.cms.common.cache.CachedEntity;
import com.wilterson.cms.common.validation.semantic.DefaultLocationValidation;
import com.wilterson.cms.common.validation.semantic.LocationNotAllowedValidation;
import com.wilterson.cms.common.validation.semantic.SemanticException;
import com.wilterson.cms.common.validation.semantic.SemanticValidator;
import com.wilterson.cms.common.validation.semantic.SemanticValidatorFactory;
import com.wilterson.cms.common.validation.semantic.UniqueGuidValidation;
import com.wilterson.cms.common.validation.semantic.UniqueNameValidation;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateSubMerchantServiceTest {

    private CacheManager cacheManager;
    private SemanticValidatorFactory semanticValidatorFactory;
    private CreateSubMerchantService createSubMerchantService;

    @BeforeEach
    void setup() {
        cacheManager = mock(CacheManager.class);
        buildSemanticValidatorFactory(cacheManager);
        buildCreateSubMerchantService(semanticValidatorFactory);
    }

    @Test
    @DisplayName("given a command " +
            "when creating a sub-merchant " +
            "then the sub-merchant semantic validator must be used")
    void whenCreatingSubMerchant_thenSubMerchantValidationMethodShouldBeCalled() {

        // given
        var command = new MerchantCommand("MerchantNameA", MULTI_MERCHANT, Collections.emptyList());

        // when
        doReturn(mock(SemanticValidator.class)).when(semanticValidatorFactory).subMerchantSemanticValidator();

        // then
        createSubMerchantService.create(command);
        verify(semanticValidatorFactory, times(1)).subMerchantSemanticValidator();
    }

    @Test
    @DisplayName("given a command with semantic violations (duplicated name, in this case) " +
            "when creating a sub-merchant " +
            "then the semantic validation should run and an exception thrown")
    void whenCreatingSubMerchant_thenSemanticValidationShouldRun() {

        // given
        var command = new MerchantCommand("MerchantNameA", MULTI_MERCHANT, Collections.emptyList());
        var cachedEntity = new CachedEntity("MerchantNameA", "SOME-GUID");

        // when
        doReturn(Optional.of(cachedEntity)).when(cacheManager).getEntityByGuid(anyString());

        // then
        assertThrows(SemanticException.class, () -> createSubMerchantService.create(command));
    }

    private void buildSemanticValidatorFactory(CacheManager cacheManager) {
        semanticValidatorFactory = spy(new SemanticValidatorFactory());
        semanticValidatorFactory.setUniqueName(new UniqueNameValidation(cacheManager));
        semanticValidatorFactory.setUniqueGuid(new UniqueGuidValidation(cacheManager));
        semanticValidatorFactory.setDefaultLocation(new DefaultLocationValidation());
        semanticValidatorFactory.setLocationNotAllowed(new LocationNotAllowedValidation());
    }

    private void buildCreateSubMerchantService(SemanticValidatorFactory semanticValidatorFactory) {
        createSubMerchantService = new CreateSubMerchantService();
        createSubMerchantService.setSemanticValidatorFactory(semanticValidatorFactory);
        createSubMerchantService.setMerchantMapper(new MerchantMapper(new LocationMapper(), new MerchantTypeMapper()));
    }
}
