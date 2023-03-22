package com.example.homechef;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homechef.model.Country;
import com.example.homechef.model.CountryModel;
import com.example.homechef.model.Post;
import com.example.homechef.model.User;
import com.example.homechef.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.homechef.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RecipeListViewAdapter extends BaseAdapter {

    private final List<Post> posts;
    private final LayoutInflater inflater;
    private final Map<String, String> mCountries = new HashMap<>();

    public RecipeListViewAdapter(Context applicationContext, List<Post> posts) {
        Locale englishLanguage = new Locale.Builder().setLanguage("en").build();
        this.posts = posts;
        this.inflater = (LayoutInflater.from(applicationContext));

        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            mCountries.put(l.getDisplayCountry(englishLanguage), iso);
        }
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View container, ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.fragment_recipe_card, null);

        final Post post = posts.get(i);
        final User user = post.getUser();

        String countryCode = mCountries.get(post.countryName);

        TextView recipeUserText = view.findViewById(R.id.recipeCardUserName);
        TextView recipeTitle = view.findViewById(R.id.recipeCardTitle);
        TextView recipeCountry = view.findViewById(R.id.recipeCardCountryName);
        TextView recipeTime = view.findViewById(R.id.recipeCardTime);
        ImageView recipeImageView = view.findViewById(R.id.recipeCardDishImg);
        ImageView countryFlagImageView = view.findViewById(R.id.countryFlag);
        ImageView userPicImageView = view.findViewById(R.id.recipeCardUserPic);

        if (post != null) {
            System.out.println(post.getCountryName());
            LiveData<Country> data = CountryModel.getInstance().getCountryByName(post.getCountryName());
            data.observeForever(new Observer<Country>() {
                @Override
                public void onChanged(Country country) {
                    if (country != null) {
                        Picasso.get().load(country.getFlag().getImageUrlPng()).resize(48, 48)
                                .into(countryFlagImageView);
                        recipeCountry.setText(country.getHebrewName());
                    } else {
                        Picasso.get().load("https://flagsapi.com/" + countryCode + "/flat/32.png").fit()
                                .into(countryFlagImageView);
                        Locale countryLocale = new Locale.Builder().setRegion(countryCode).build();
                        Locale hebrewLanguage = new Locale.Builder().setLanguage("he").build();

                        recipeCountry.setText(countryLocale.getDisplayCountry(hebrewLanguage));
                    }
                    data.removeObserver(this);
                }
            });

            if(!post.getDishImg().isEmpty() && post.getDishImg() != null){
                Picasso.get().load(post.getDishImg()).into(recipeImageView);
            }

            recipeTitle.setText(post.getTitle());
            recipeTime.setText(Utils.timeToString(post.getTime()));
        }

        System.out.println(user);
        if (user != null) {
            System.out.println(user.getAvatarUrl());
            if (!user.getAvatarUrl().isEmpty()) {
                Picasso.get().load(post.user.getAvatarUrl()).resize(50, 50).centerCrop().into(userPicImageView);
            } else {
                Picasso.get().load("https://robohash.org/" + post.user.getUserName()).resize(50, 50).centerCrop()
                        .into(userPicImageView);
            }
            recipeUserText.setText(user.getUserName());
        }
        return view;
    }
}