package com.lootchaser.domain;

import java.time.OffsetDateTime;

public record Game(
    Long id,
    String title,
    String genre,
    Double basePrice,
    Long storeId,
    OffsetDateTime createdAt
) {}