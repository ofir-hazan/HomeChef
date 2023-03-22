package com.example.homechef;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homechef.model.Country;
import com.example.homechef.model.CountryModel;
import com.example.homechef.model.Model;
import com.example.homechef.model.Post;
import com.example.homechef.model.User;
import com.example.homechef.utils.Utils;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DisplayEditRecipeFragment extends Fragment {

    private static final String ARG_POST_ID = "postId";

    private String postId;
    private Post currPost;

    private final Map<String, String> mCountries = new HashMap<>();

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

        Model.instance().getPostById(postId, (post) -> {
            currPost = post;

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
                    saveChanges(view, currPost);
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetFragment(view);
                }
            });

            if (currPost != null) {
                TextView recipeTitle = (TextView) view.findViewById(R.id.recipeTitle);
                TextView recipeTime = (TextView) view.findViewById(R.id.recipeTime);
                ImageView countryFlag = (ImageView) view.findViewById(R.id.countryFlag);
                TextView recipeCountry = (TextView) view.findViewById(R.id.recipeCountry);
                ImageView image = (ImageView) view.findViewById(R.id.image);
                ImageView userPic = (ImageView) view.findViewById(R.id.userPic);
                TextView recipeUser = (TextView) view.findViewById(R.id.recipeUser);
                EditText recipeDescription = (EditText) view.findViewById(R.id.recipeDescription);

                recipeTitle.setText(currPost.getTitle());
                recipeTime.setText(Utils.timeToString(currPost.getTime()));
                recipeDescription.setText(currPost.fullRecipe);
                LiveData<Country> data = CountryModel.getInstance().getCountryByName(currPost.getCountryName());
                String countryCode = mCountries.get(currPost.countryName);
                data.observeForever(new Observer<Country>() {
                    @Override
                    public void onChanged(Country country) {
                        if (country != null) {
                            Picasso.get().load(country.getFlag().getImageUrlPng()).resize(48, 48)
                                    .into(countryFlag);
                            recipeCountry.setText(country.getHebrewName());
                        } else {
                            Picasso.get().load("https://flagsapi.com/" + mCountries.get(currPost.countryName) + "/flat/32.png").fit()
                                    .into(countryFlag);
                            Locale countryLocale = new Locale.Builder().setRegion(countryCode).build();
                            Locale hebrewLanguage = new Locale.Builder().setLanguage("he").build();

                            recipeCountry.setText(countryLocale.getDisplayCountry(hebrewLanguage));
                        }
                        data.removeObserver(this);
                    }
                });

                if(!currPost.getDishImg().isEmpty() && currPost.getDishImg() != null){
                    Picasso.get().load(currPost.getDishImg()).into(image);
                }

                final User user = currPost.user;
                recipeUser.setText(user.getUserName());
                if (currPost.getDishImg() != null && currPost.getDishImg().length() > 5) {
                    Picasso.get().load(currPost.getDishImg()).placeholder(R.drawable.icon_person).into(userPic);
                } else {
                    userPic.setImageResource(R.drawable.icon_person);
                }
            }
        });

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

    public void saveChanges(View v, Post currPost) {
        EditText recipeDescription = (EditText) v.findViewById(R.id.recipeDescription);
        String recipeDesc = recipeDescription.getText().toString().trim();

        currPost.setFullRecipe(recipeDesc);
        Model.instance().addPost(currPost, post -> {
            resetFragment(v);
        });
    }
}