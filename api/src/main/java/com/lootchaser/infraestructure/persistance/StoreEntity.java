package com.lootchaser.infraestructure.persistance;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.OffsetDateTime;


@Entity
@Table(name = "store")
public class StoreEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false, unique = true)
    public String domain;

    @Column(name = "base_url", nullable = false)
    public String baseURL;

    @Column(name = "created_at", nullable = false, updatable = false)
    public OffsetDateTime createdAt;
    
}
