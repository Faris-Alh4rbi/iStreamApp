package com.example.istream;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    User findByUsername(String username);

    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("UPDATE users SET password = :passwordHash WHERE id = :id")
    void updatePasswordHash(int id, String passwordHash);
}
