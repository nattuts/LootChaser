package com.lootchaser.domain;

import java.time.OffsetDateTime;

/*entidade de domino (jogo)*/

public record Game(
    Long id,
    String title,
    String genre,
    Double basePrice,
    Long storeId,
    OffsetDateTime createdAt
) {}