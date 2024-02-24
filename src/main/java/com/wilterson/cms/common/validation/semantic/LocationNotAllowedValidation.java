package com.wilterson.cms.common.validation.semantic;

import static com.wilterson.cms.common.validation.IssueFactory.issue;

import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.common.validation.Issue;
import com.wilterson.cms.common.validation.IssueFactory;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
public class LocationNotAllowedValidation implements SemanticValidator<Merchant> {

    @Override
    public void validate(Merchant merchant, Set<Issue> semanticIssues) {

        if (!CollectionUtils.isEmpty(merchant.getLocations())) {
            semanticIssues.add(issue(IssueFactory.MERCHANT_LOCATIONS.getReasonCode(), "Parent merchant can't have location.", false));
        }
    }
}
