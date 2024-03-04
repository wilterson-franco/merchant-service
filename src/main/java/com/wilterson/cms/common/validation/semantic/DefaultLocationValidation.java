package com.wilterson.cms.common.validation.semantic;

import static com.wilterson.cms.common.validation.IssueFactory.issue;

import com.wilterson.cms.application.domain.model.Location;
import com.wilterson.cms.application.domain.model.Merchant;
import com.wilterson.cms.common.validation.Issue;
import com.wilterson.cms.common.validation.IssueFactory;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
public class DefaultLocationValidation implements SemanticValidator<Merchant> {

    @Override
    public void validate(Merchant merchant, Set<Issue> semanticIssues) {

        var count = countDefaultLocations(merchant);

        if (count < 1) {
            semanticIssues.add(issue(IssueFactory.MERCHANT_LOCATIONS_DEFAULT.getReasonCode(), "Default location is required.", false));
        } else if (count > 1) {
            semanticIssues.add(issue(IssueFactory.MERCHANT_LOCATIONS_DEFAULT.getReasonCode(), "Only one location must be set as default.", false));
        }
    }

    private static long countDefaultLocations(Merchant merchant) {

        List<Location> locations = merchant.getLocations();

        if (!CollectionUtils.isEmpty(locations)) {
            return locations.stream().filter(Location::isDefault).count();
        }

        return 0;
    }
}
