package com.example.homechef.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("select * from users")
    LiveData<List<User>> getAll();

    @Query("select * from users where id = :userId")
    User getUserById(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
