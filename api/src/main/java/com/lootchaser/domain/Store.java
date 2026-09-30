package com.lootchaser.domain;

import java.time.OffsetDateTime;

public record Store (
    Long id,
    String name,
    String domain,
    String baseURL,
    OffsetDateTime createdAt
) {}
