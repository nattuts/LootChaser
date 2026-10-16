package com.lootchaser.domain;

import java.util.List;
import java.util.Optional;

public interface StoreRepository {
    List<Store> listAll(int pageID, int pageSize, String nameFilter);
    Optional<Store> findById(Long id);
    Store save(Store store);
    Store update(Long id, Store store);
    boolean deleteById(Long id);
}; 
