package com.wilterson.cms.common.validation.semantic;

import static com.wilterson.cms.common.validation.IssueFactory.issue;

import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.common.cache.CacheManager;
import com.wilterson.cms.common.cache.CachedEntity;
import com.wilterson.cms.common.validation.Issue;
import com.wilterson.cms.common.validation.IssueFactory;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueGuidValidation implements SemanticValidator<Merchant> {

    private final CacheManager cacheManager;

    @Override
    public void validate(Merchant merchant, Set<Issue> semanticIssues) {
        Optional<CachedEntity> existentMerchant = cacheManager.getEntityByGuid(merchant.getGuid());
        existentMerchant.ifPresent((entity) -> semanticIssues.add(issue(IssueFactory.MERCHANT_GUID.getReasonCode(), "Merchant GUID must be unique.", false)));
    }
}
