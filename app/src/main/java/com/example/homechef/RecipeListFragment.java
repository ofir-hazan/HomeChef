package com.example.homechef;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.ListFragment;
import androidx.lifecycle.LiveData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.homechef.model.Model;
import com.example.homechef.model.Post;
import com.example.homechef.model.PostCard;
import com.example.homechef.model.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RecipeListFragment extends ListFragment {
        PostsListFragmentViewModel viewModel;

        public RecipeListFragment() {
        }

        public static RecipeListFragment newInstance(int columnCount) {
                RecipeListFragment fragment = new RecipeListFragment();
                return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                        Bundle savedInstanceState) {
                final Activity activity = getActivity();
                final Context context = getContext();
//                User currUser = new User();

        //      ListView view = (ListView) inflater.inflate(R.layout.fragment_recipe_list, container, false);
//                String currEmail = Model.instance().getConnectedUser();
//                Model.instance().getUserById(currEmail, (user) -> {
//                        currUser = new User(user.getEmail(), user.getUserName(), user.getAvatarUrl());
//                });
//                PostCard postCard = new PostCard(viewModel.getData().getValue().get(0), currUser);
//                PostRecyclerAdapter adapter = new PostRecyclerAdapter(getLayoutInflater(), viewModel.getData().getValue());

                if (context == null || activity == null) {
                        return super.onCreateView(inflater, container, savedInstanceState);
                }

                List<Post> fakeData = new LinkedList<>();
                User fakeUser = new User("d@gmail.com", "d",
                                "https://www.delscookingtwist.com/wp-content/uploads/2022/01/Easy-Fluffy-American-Pancakes_1.jpg");

                fakeData.add(new Post("1", "פנקייקים",
                                fakeUser,
                                "https://www.delscookingtwist.com/wp-content/uploads/2022/01/Easy-Fluffy-American-Pancakes_1.jpg",
                                "Israel", 1640L,""));
                fakeData.add(new Post("2", "גלידה", fakeUser,
                                "https://www.cnet.com/a/img/resize/989e8e3be4eb8baae522f982b7cc1f6a3f4c0f6d/hub/2022/12/14/8af299d7-0c8f-493f-9771-c5b4738cb690/gettyimages-1306753442.jpg?auto=webp&fit=crop&height=675&width=1200",
                                "Israel", 3600L,""));

                fakeData.add(new Post("3", "דג סלמון בתנור",
                                fakeUser,
                                "https://usercontent1.hubstatic.com/8934992.jpg",
                                "Israel", 250L,""));

                fakeData.add(new Post("4", "פנקייקים", fakeUser,
                                "https://www.delscookingtwist.com/wp-content/uploads/2022/01/Easy-Fluffy-American-Pancakes_1.jpg",
                                "Israel", 300L,""));

                fakeData.add(new Post("5", "פנקייקים", fakeUser,
                                "https://www.delscookingtwist.com/wp-content/uploads/2022/01/Easy-Fluffy-American-Pancakes_1.jpg",
                                "Israel", 600L,""));

                // Populate the list fragment
                LiveData<List<Post>> dbData = Model.instance().getAllPosts();

                dbData.observe(getViewLifecycleOwner(), posts -> {
                        if (posts == null) {
                                setListAdapter(new RecipeListViewAdapter(context,
                                                fakeData));
                        } else {
                                setListAdapter(new RecipeListViewAdapter(context,
                                                dbData.getValue()));
                        }
                });
                return super.onCreateView(inflater, container, savedInstanceState);
        }
}