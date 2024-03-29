package com.example.homechef.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PostDAO {
    @Query("select * from posts")
    LiveData<List<Post>> getAll();

    @Query("select * from posts where email = :email")
    LiveData<List<Post>> getPostsByEmail(String email);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Post... Posts);

    @Delete
    void delete(Post Post);

}
