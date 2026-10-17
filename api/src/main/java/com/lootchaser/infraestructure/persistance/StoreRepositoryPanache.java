package com.lootchaser.infraestructure.persistance;

import com.lootchaser.domain.Store;
import com.lootchaser.domain.StoreRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class StoreRepositoryPanache implements StoreRepository {

    @Override
    public List<Store> listAll(int pageIndex, int pageSize, String nameFilter) {
        PanacheQuery<StoreEntity> query;
        if (nameFilter != null && !nameFilter.isBlank()) {
            query = StoreEntity.find("LOWER(name) LIKE LOWER(?1)", "%" + nameFilter + "%");
        } else {
            query = StoreEntity.findAll();
        }
        return query.page(Page.of(pageIndex, pageSize))
                .list()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Store> findById(Long id) {
        StoreEntity entity = StoreEntity.findById(id);
        return Optional.ofNullable(entity).map(this::toDomain);
    }

    @Override
    @Transactional
    public Store save(Store store) {
        StoreEntity entity = new StoreEntity();
        entity.name = store.name();
        entity.domain = store.domain();
        entity.baseURL = store.baseURL();
        entity.createdAt = OffsetDateTime.now();
        entity.persist();
        return toDomain(entity);
    }

    @Override
    @Transactional
    public Store update(Long id, Store store) {
        StoreEntity entity = StoreEntity.findById(id);
        if (entity == null) return null;
        entity.name = store.name();
        entity.domain = store.domain();
        entity.baseURL = store.baseURL();
        return toDomain(entity);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        return StoreEntity.deleteById(id);
    }

    private Store toDomain(StoreEntity entity) {
        return new Store(entity.id, entity.name, entity.domain, entity.baseURL, entity.createdAt);
    }
}