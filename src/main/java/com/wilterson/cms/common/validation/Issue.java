package com.wilterson.cms.common.validation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Issue {

    private String reasonCode;
    private String source;
    private String description;
    private boolean recoverable;
}
