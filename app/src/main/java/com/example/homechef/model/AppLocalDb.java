package com.example.homechef.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.homechef.MyApplication;


@Database(entities = {User.class, Post.class}, version = 82)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract PostDAO PostDao();
    public abstract UserDAO UserDao();
}

public class AppLocalDb{
    static public AppLocalDbRepository getAppDb() {
        return Room.databaseBuilder(MyApplication.getMyContext(),
                        AppLocalDbRepository.class,
                        "dbFileName.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    private AppLocalDb(){}
}

