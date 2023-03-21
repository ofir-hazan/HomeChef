package com.example.homechef;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.homechef.model.Model;
import com.example.homechef.model.Post;
import com.example.homechef.model.User;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link DisplayEditRecipeFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class DisplayEditRecipeFragment extends Fragment {

    private static final String ARG_POST_ID = "postId";

    private String postId;

//    public DisplayEditRecipeFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param postId Parameter 1.
//     * @return A new instance of fragment DisplayEditRecipeFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static DisplayEditRecipeFragment newInstance(String postId) {
//        DisplayEditRecipeFragment fragment = new DisplayEditRecipeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_POST_ID, postId);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            postId = getArguments().getString(ARG_POST_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_edit_recipe, container, false);

        Button editButton = (Button) view.findViewById(R.id.editButton);
        Button saveButton = (Button) view.findViewById(R.id.saveButton);
        Button cancelButton = (Button) view.findViewById(R.id.cancelButton);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button editButton = (Button) view.findViewById(R.id.editButton);
                editButton.setVisibility(View.INVISIBLE);
                Button saveButton = (Button) view.findViewById(R.id.saveButton);
                saveButton.setVisibility(View.VISIBLE);
                Button cancelButton = (Button) view.findViewById(R.id.cancelButton);
                cancelButton.setVisibility(View.VISIBLE);
                EditText recipeDescription = (EditText) view.findViewById(R.id.recipeDescription);
                recipeDescription.setEnabled(true);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges(view);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFragment(view);
            }
        });

        // TODO: get the post details from the list
        // Inflate the layout for this fragment
        return view;
    }

    private void resetFragment(View v) {
        Button editButton = (Button) v.findViewById(R.id.editButton);
        editButton.setVisibility(View.VISIBLE);
        Button saveButton = (Button) v.findViewById(R.id.saveButton);
        saveButton.setVisibility(View.INVISIBLE);
        Button cancelButton = (Button) v.findViewById(R.id.cancelButton);
        cancelButton.setVisibility(View.INVISIBLE);
        EditText recipeDescription = (EditText) v.findViewById(R.id.recipeDescription);
        recipeDescription.setEnabled(false);
    }

    public void saveChanges(View v) {
        EditText recipeDescription = (EditText) v.findViewById(R.id.recipeDescription);
        String recipeDesc = recipeDescription.getText().toString().trim();

        // TODO: edit the recipe description and save to DB
        Post currPost = new Post();
//        Model.instance().addPost();
    }
}