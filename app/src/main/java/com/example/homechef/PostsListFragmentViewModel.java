package com.example.homechef;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.homechef.model.Model;
import com.example.homechef.model.Post;

import java.util.List;

public class PostsListFragmentViewModel extends ViewModel {

    private final LiveData<List<Post>> data = Model.instance().getAllPosts();

    LiveData<List<Post>> getData(){
        return data;
    }
}
