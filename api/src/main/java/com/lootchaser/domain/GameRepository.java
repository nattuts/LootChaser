package com.lootchaser.domain;

import java.util.List;
import java.util.Optional;

public interface GameRepository {
    List<Game> listAll(int pageID, int pageSize, String titleFilter, Long storeIdFilter);
    Optional<Game> findById(Long id);
    Game save(Game game);
    Game update(Long id, Game game);
    boolean deleteById(Long id);
}; 

