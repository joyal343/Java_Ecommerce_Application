package com.joyal.catalog_service.dao;

import com.joyal.catalog_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserDao extends JpaRepository<User, UUID> {
}
