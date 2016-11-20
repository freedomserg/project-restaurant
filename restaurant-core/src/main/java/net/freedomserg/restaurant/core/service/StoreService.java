package net.freedomserg.restaurant.core.service;

import net.freedomserg.restaurant.core.model.dao.StoreDao;
import net.freedomserg.restaurant.core.model.entity.Store;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class StoreService {

    private StoreDao storeDao;

    public void setStoreDao(StoreDao storeDao) {
        this.storeDao = storeDao;
    }

    @Transactional
    public Integer add(Store store) {
        return storeDao.save(store);
    }

    @Transactional
    public void delete(Store store) {
        storeDao.remove(store);
    }

    @Transactional
    public void update(Store store) {
        storeDao.update(store);
    }

    @Transactional
    public Store getByName(String name) {
        return storeDao.loadByName(name);
    }

    @Transactional
    public Store getById(int id) {
        return storeDao.loadById(id);
    }

    @Transactional
    public List<Store> getAll() {
        return storeDao.loadAll();
    }
}
