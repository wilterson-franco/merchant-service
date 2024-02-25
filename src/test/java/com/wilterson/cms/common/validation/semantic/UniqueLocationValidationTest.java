package com.wilterson.cms.common.validation.semantic;

import static org.assertj.core.api.Assertions.assertThat;

import com.wilterson.cms.application.domain.model.Location;
import com.wilterson.cms.application.domain.model.Location.LocationBuilder;
import com.wilterson.cms.common.validation.Issue;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class UniqueLocationValidationTest {

    private UniqueLocationValidation uniqueLocationValidation = new UniqueLocationValidation();

    @Test
    void whenLocationExistent_thenShouldThrowException() {

        // given
        Set<Issue> issues = new HashSet<>();
        Location location = new LocationBuilder("CAN").build();

        // when
        uniqueLocationValidation.validate(location, issues);

        // then
        assertThat(issues).hasSize(1);
    }
}
