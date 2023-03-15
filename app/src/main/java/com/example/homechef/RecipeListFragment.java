package com.example.homechef;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class RecipeListFragment extends ListFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RecipeListFragment newInstance(int columnCount) {
        RecipeListFragment fragment = new RecipeListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Activity activity = getActivity();
        final Context context = getContext();

        ListView view = (ListView) inflater.inflate(R.layout.fragment_item_list, container, false);

        if(context == null || activity == null) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        List<Post> fakeData = new LinkedList<>();
        fakeData.add( new Post("פנקייקים",
                "Meshie",
                "https://www.delscookingtwist.com/wp-content/uploads/2022/01/Easy-Fluffy-American-Pancakes_1.jpg",
                "https://www.delscookingtwist.com/wp-content/uploads/2022/01/Easy-Fluffy-American-Pancakes_1.jpg",
                "Italy", "time"));
        fakeData.add( new Post("פנקייקים",
                "Ofir",
                "https://www.delscookingtwist.com/wp-content/uploads/2022/01/Easy-Fluffy-American-Pancakes_1.jpg",
                "https://www.delscookingtwist.com/wp-content/uploads/2022/01/Easy-Fluffy-American-Pancakes_1.jpg",
                "Israel", "time"));
        fakeData.add( new Post("פנקייקים",
                "Ofir",
                "https://www.delscookingtwist.com/wp-content/uploads/2022/01/Easy-Fluffy-American-Pancakes_1.jpg",
                "https://www.delscookingtwist.com/wp-content/uploads/2022/01/Easy-Fluffy-American-Pancakes_1.jpg",
                "Israel", "time"));
        fakeData.add( new Post("פנקייקים",
                "Ofir",
                "https://www.delscookingtwist.com/wp-content/uploads/2022/01/Easy-Fluffy-American-Pancakes_1.jpg",
                "https://www.delscookingtwist.com/wp-content/uploads/2022/01/Easy-Fluffy-American-Pancakes_1.jpg",
                "Israel", "time"));
        fakeData.add( new Post("פנקייקים",
                "Meshie",
                "https://www.delscookingtwist.com/wp-content/uploads/2022/01/Easy-Fluffy-American-Pancakes_1.jpg",
                "https://www.delscookingtwist.com/wp-content/uploads/2022/01/Easy-Fluffy-American-Pancakes_1.jpg",
                "Italy", "time"));



        //Populate the list fragment
        setListAdapter(new RecipeListViewAdapter(context,
                fakeData));
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}