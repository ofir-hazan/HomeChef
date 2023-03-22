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

    public PostCard(Post post, User user) {
        this.post = post;
        this.user = user;
    }

    @Override
    public String toString() {
        return "PostCard{" +
                "post=" + post +
                ", user=" + user +
                '}';
    }
}
