package spring.service;

import spring.model.User;

/**
 * Created by dozac on 15/04/2015.
 */
public interface UserService {
    public User getUser(String login);
}
