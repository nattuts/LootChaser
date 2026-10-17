package com.lootchaser.domain;

import java.time.OffsetDateTime;

/*entidade de dominio (loja) */

public record Store (
    Long id,
    String name,
    String domain,
    String baseURL,
    OffsetDateTime createdAt
) {}
