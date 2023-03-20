package com.example.homechef;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.homechef.model.Model;
import com.example.homechef.model.Post;
import com.example.homechef.model.PostCard;

import java.util.List;

public class PostsListFragmentViewModel extends ViewModel {
    private final LiveData<List<PostCard>> data = Model.instance().getAllPosts();

    LiveData<List<PostCard>> getData(){
        return data;
    }
}
