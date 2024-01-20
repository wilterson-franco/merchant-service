package com.wilterson.cms.application.domain.model;

import com.wilterson.cms.common.validation.Validatable;

public record Location(boolean isDefault) implements Validatable {

}
