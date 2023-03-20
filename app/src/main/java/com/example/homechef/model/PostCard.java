package com.example.homechef.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class PostCard {
    @Embedded public Post post;
    @Relation(
            parentColumn = "userId",
            entityColumn = "id"
    )
    public User user;
}
