package com.example.homechef;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.ListFragment;
import androidx.lifecycle.LiveData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homechef.model.Model;
import com.example.homechef.model.Post;

import java.util.List;

public class MyRecipesFragment extends ListFragment {

    public MyRecipesFragment() {
    }

    public static MyRecipesFragment newInstance(int columnCount) {
        MyRecipesFragment fragment = new MyRecipesFragment();
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

        if (context == null || activity == null) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        // Populate the list fragment
        LiveData<List<Post>> dbData = Model.instance().getUserPosts();

        dbData.observe(getViewLifecycleOwner(), posts -> {
            if (posts != null) {
                setListAdapter(new RecipeListViewAdapter(context,
                        dbData.getValue()));
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}