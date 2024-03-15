/*
 * Copyright 2024 Wilterson Franco
 */

package com.wilterson.cms.common.validation.constraint;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.wilterson.cms.common.validation.constraint.DefaultRequired.List;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = DefaultLocationCommandRequiredValidator.class)
@Documented
@Repeatable(List.class)
public @interface DefaultRequired {

    String message() default "{merchant.location.default-required}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    DefaultLocationsQuantity value();

    @Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        DefaultRequired[] value();
    }
}
