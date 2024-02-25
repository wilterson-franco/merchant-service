package com.wilterson.cms.common.validation.semantic;

import com.wilterson.cms.application.domain.model.Location;
import com.wilterson.cms.common.validation.Issue;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class UniqueLocationValidation implements SemanticValidator<Location> {

    @Override
    public void validate(Location location, Set<Issue> semanticIssues) {

        // TODO: to be implemented

    }
}
