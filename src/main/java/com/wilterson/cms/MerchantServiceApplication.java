/*
 * Copyright 2024 Wilterson Franco
 */

package com.wilterson.cms;

import com.wilterson.cms.application.port.in.CreateSubMerchantUseCase;
import com.wilterson.cms.application.port.in.LocationCommand;
import com.wilterson.cms.application.port.in.SubMerchantCommand;
import jakarta.validation.ConstraintViolationException;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
public class MerchantServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MerchantServiceApplication.class, args);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }
}

@Component
class MyRunner implements CommandLineRunner {

    private CreateSubMerchantUseCase createSubMerchantUseCase;

    @Autowired
    public void setCreateSubMerchantUseCase(CreateSubMerchantUseCase createSubMerchantUseCase) {
        this.createSubMerchantUseCase = createSubMerchantUseCase;
    }

    @Override
    public void run(String... args) {

        var subMerchantCommandWithoutName = new SubMerchantCommand(null, Collections.singletonList(new LocationCommand("CAN", true)));

        try {
            createSubMerchantUseCase.executeCommand(subMerchantCommandWithoutName);
        } catch (ConstraintViolationException e) {
            System.out.println("<<< CONSTRAINT VIOLATION >>>");
            return;
        }

        System.out.println("<<< NO EXCEPTIONS >>>");
    }
}