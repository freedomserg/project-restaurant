package net.freedomserg.restaurant.core.service;

import net.freedomserg.restaurant.core.model.dao.UserDao;
import net.freedomserg.restaurant.core.model.entity.User;
import org.springframework.transaction.annotation.Transactional;

public class UserService {

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public User getById(int id) {
        return userDao.findById(id);
    }

    @Transactional
    public User getBySso(String sso) {
        return userDao.findBySso(sso);
    }
}
