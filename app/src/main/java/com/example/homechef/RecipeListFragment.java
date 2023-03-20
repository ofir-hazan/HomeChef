package com.example.homechef;

import android.app.Activity;
import android.content.Context;
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

        ListView view = (ListView) inflater.inflate(R.layout.fragment_recipe_list, container, false);

        if(context == null || activity == null) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        List<Post> fakeData = new LinkedList<>();

        User fakeUser = new User("ofir@gmail.com","androidx.appcompat.widget.AppCompatImageView{9f9fc57 V.ED..... ........ 306,198-774,666 #7f0901fc app:id/uploadProfilePicture}","ofir");

        fakeData.add(new Post("פנקייקים",
                "https://www.delscookingtwist.com/wp-content/uploads/2022/01/Easy-Fluffy-American-Pancakes_1.jpg",
                "Israel", 1640L, fakeUser));

        fakeData.add( new Post("גלידה",
                "https://www.cnet.com/a/img/resize/989e8e3be4eb8baae522f982b7cc1f6a3f4c0f6d/hub/2022/12/14/8af299d7-0c8f-493f-9771-c5b4738cb690/gettyimages-1306753442.jpg?auto=webp&fit=crop&height=675&width=1200",
                "Israel", 3600L, fakeUser));

        fakeData.add( new Post("דג סלמון בתנור",
                "https://usercontent1.hubstatic.com/8934992.jpg",
                "Israel", 250L, fakeUser));

        fakeData.add( new Post("פנקייקים",
                "https://www.delscookingtwist.com/wp-content/uploads/2022/01/Easy-Fluffy-American-Pancakes_1.jpg",
                "Israel", 300L, fakeUser));

        fakeData.add( new Post("פנקייקים",
                "https://www.delscookingtwist.com/wp-content/uploads/2022/01/Easy-Fluffy-American-Pancakes_1.jpg",
                "Israel", 600L, fakeUser));

        //Populate the list fragment
        LiveData<List<Post>> dbData = Model.instance().getAllPosts();

        System.out.println("live data from db: "+dbData);
        System.out.println("live data from db value: "+dbData.getValue());
        if (dbData.getValue() == null){
            setListAdapter(new RecipeListViewAdapter(context,
                    fakeData));
        }else{
            setListAdapter(new RecipeListViewAdapter(context,
                    dbData.getValue()));
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}