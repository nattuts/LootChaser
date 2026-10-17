package com.lootchaser.infraestructure.persistance;

import com.lootchaser.domain.Game;
import com.lootchaser.domain.GameRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class GameRepositoryPanache implements GameRepository {

    @Override
    public List<Game> listAll(int pageIndex, int pageSize, String titleFilter, Long storeIdFilter) {
        PanacheQuery<GameEntity> query;
        if (titleFilter != null && !titleFilter.isBlank() && storeIdFilter != null) {
            query = GameEntity.find("LOWER(title) LIKE LOWER(?1) AND store.id = ?2", "%" + titleFilter + "%", storeIdFilter);
        } else if (titleFilter != null && !titleFilter.isBlank()) {
            query = GameEntity.find("LOWER(title) LIKE LOWER(?1)", "%" + titleFilter + "%");
        } else if (storeIdFilter != null) {
            query = GameEntity.find("store.id", storeIdFilter);
        } else {
            query = GameEntity.findAll();
        }

        return query.page(Page.of(pageIndex, pageSize))
                .list()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Game> findById(Long id) {
        GameEntity entity = GameEntity.findById(id);
        return Optional.ofNullable(entity).map(this::toDomain);
    }

    @Override
    @Transactional
    public Game save(Game game) {
        StoreEntity storeEntity = StoreEntity.findById(game.storeId());
        if (storeEntity == null) {
            throw new IllegalArgumentException("Loja com ID " + game.storeId() + " não foi encontrada.");
        }

        GameEntity entity = new GameEntity();
        entity.title = game.title();
        entity.genre = game.genre();
        entity.basePrice = game.basePrice();
        entity.store = storeEntity;
        entity.createdAt = OffsetDateTime.now();
        entity.persist();

        return toDomain(entity);
    }

    @Override
    @Transactional
    public Game update(Long id, Game game) {
        GameEntity entity = GameEntity.findById(id);
        if (entity == null) return null;

        StoreEntity storeEntity = StoreEntity.findById(game.storeId());
        if (storeEntity == null) {
            throw new IllegalArgumentException("Loja com ID " + game.storeId() + " não foi encontrada.");
        }

        entity.title = game.title();
        entity.genre = game.genre();
        entity.basePrice = game.basePrice();
        entity.store = storeEntity;

        return toDomain(entity);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        return GameEntity.deleteById(id);
    }

    private Game toDomain(GameEntity entity) {
        return new Game(
                entity.id,
                entity.title,
                entity.genre,
                entity.basePrice,
                entity.store.id,
                entity.createdAt
        );
    }
}
