package com.example.homechef;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.homechef.model.Country;
import com.example.homechef.model.CountryModel;
import com.example.homechef.model.Model;
import com.example.homechef.model.Post;
import com.example.homechef.model.User;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.ArrayList;
import java.util.UUID;

public class AddNewRecipeFragment extends Fragment {

    private MaterialAutoCompleteTextView countriesAutocompleteTextView;
    private final ArrayList<String> countries = new ArrayList<>();
    private Button saveButton, cancelButton, uploadPic;
    private EditText titleText, timeText, fullRecipeText;
    private String imgUrl="";

    private static Post newPost;

    public AddNewRecipeFragment() {
        // Required empty public constructor
    }

    public static AddNewRecipeFragment newInstance() {
        AddNewRecipeFragment fragment = new AddNewRecipeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_add_new_recipe, container, false);
        countriesAutocompleteTextView = view.findViewById(R.id.countryDropdown);
        useApi(container);
        saveButton=view.findViewById(R.id.save_recipe_button);
        saveButton.setOnClickListener(v->{
            onSave();
        });
        cancelButton=view.findViewById(R.id.cancel_recipe_button);
        cancelButton.setOnClickListener(v->{
            //TODO add navigation to main
        });
        uploadPic=view.findViewById(R.id.uploadPicButton);
        titleText=view.findViewById(R.id.recipeTitle);
        timeText=view.findViewById(R.id.estimatedTime);
        fullRecipeText=view.findViewById(R.id.fullRecipe);
        return view;
    }

    private void useApi(ViewGroup container) {
        CountryModel.getInstance().getAllCountries().observe(getViewLifecycleOwner(), countries -> {
            this.countries.clear();

            for(Country country : countries) {
                this.countries.add(country.getName());
            }

            countriesAutocompleteTextView.setSimpleItems(this.countries.toArray(new String[0]));
        });
    }


    public void onSave() {
        User connectedUser = new User();
         Model.instance().getUserById(Model.instance().getConnectedUser(), (user) -> { connectedUser.setEmail(user.getEmail());
             connectedUser.setUserName(user.userName);
             connectedUser.setAvatarUrl(user.avatarUrl);
             newPost = new Post(UUID.randomUUID().toString(),
                     titleText.getText().toString(),
                     connectedUser,
                     imgUrl,
                     countriesAutocompleteTextView.getText().toString(),
                     Long.parseLong(timeText.getText().toString()), fullRecipeText.getText().toString());
             Model.instance().addPost(newPost, (newPost)->{
                 navToMainFragment();
             });
        });
    }

    private void navToMainFragment() {
        NavHostFragment finalHost = NavHostFragment.create(R.navigation.nav_graph);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.main_navhost, finalHost)
                .setPrimaryNavigationFragment(finalHost) // equivalent to app:defaultNavHost="true"
                .commit();
    }
}