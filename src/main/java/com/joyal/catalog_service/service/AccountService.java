package com.joyal.catalog_service.service;

import com.joyal.catalog_service.dao.UserDao;
import com.joyal.catalog_service.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final UserDao userDao;

    public AccountService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }
}
