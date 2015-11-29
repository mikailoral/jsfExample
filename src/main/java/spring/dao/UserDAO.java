package spring.dao;

import spring.model.User;

/**
 * Created by dozac on 15/04/2015.
 */
public interface UserDAO {
    public User getUser(String login);
}
