package com.example.homechef;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homechef.placeholder.PlaceholderContent.PlaceholderItem;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class RecipeListViewAdapter extends BaseAdapter {

    private final List<Post> posts;
    private final LayoutInflater inflater;
    private Map<String, String> mCountries = new HashMap<>();


    public RecipeListViewAdapter(Context applicationContext, List<Post> posts) {
        this.posts = posts;
        this.inflater = (LayoutInflater.from(applicationContext));

        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            mCountries.put(l.getDisplayCountry(), iso);
            System.out.println(iso);
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.fragment_recipe_card, null);

        final Post post = posts.get(i);
        String countryCode = mCountries.get(post.countryName);

        TextView recipeUserText = view.findViewById(R.id.recipeUser);
        TextView recipeTitle = view.findViewById(R.id.recipeTitle);
        ImageView recipeImageView = view.findViewById(R.id.image);
        TextView recipeCountry = view.findViewById(R.id.recipeCountry);
        ImageView countryFlagImageView = view.findViewById(R.id.countryFlag);
        ImageView userPicImageView = view.findViewById(R.id.userPic);

        new DownloadImageTask(countryFlagImageView).execute("https://flagsapi.com/" + countryCode + "/flat/32.png");
        new DownloadImageTask(userPicImageView).execute(post.userImg);
        new DownloadImageTask(recipeImageView).execute(post.dishImg);

        Locale country = new Locale.Builder().setRegion(countryCode).build();
        Locale hebrewLanguage = new Locale.Builder().setLanguage("he").build();

        recipeCountry.setText(country.getDisplayCountry(hebrewLanguage));
        recipeTitle.setText(post.title);
        recipeUserText.setText(post.userName);

        return view;
    }
}