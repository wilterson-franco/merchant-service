/*
 * Copyright 2024 Wilterson Franco
 */

package com.wilterson.cms.common.cache;

import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    public Set<CachedEntity> merchantCache() {
        return Set.of(new CachedEntity("Daniel", "DDDDDD"),
                new CachedEntity("Gabriel", "GGGGGG"),
                new CachedEntity("Suellen", "SSSSSS"),
                new CachedEntity("Wilterson", "WWWWWW"));
    }
}
