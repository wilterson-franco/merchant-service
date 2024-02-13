package com.wilterson.cms.application.domain.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Builder
public class ApplicationException extends RuntimeException {

    private String reasonCode;
    private String source;
    private String message;
    private boolean recoverable;
}
