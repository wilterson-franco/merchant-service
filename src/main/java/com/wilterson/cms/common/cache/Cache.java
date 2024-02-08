package com.wilterson.cms.common.cache;

import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Cache {

    private final Set<CachedEntity> cachedEntitySet;

    public Optional<CachedEntity> getEntityByName(String name) {
        return cachedEntitySet.stream().filter(entity -> entity.name().equalsIgnoreCase(name)).findFirst();
    }

    public Optional<CachedEntity> getEntityByGuid(String guid) {
        return cachedEntitySet.stream().filter(entity -> entity.guid().equalsIgnoreCase(guid)).findFirst();
    }
}
