package net.freedomserg.restaurant.core.model.dao;

import net.freedomserg.restaurant.core.model.entity.User;

public interface UserDao {

    User findById(int id);

    User findBySso(String sso);
}
