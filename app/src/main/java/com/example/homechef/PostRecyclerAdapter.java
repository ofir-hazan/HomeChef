package com.example.homechef;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homechef.model.Country;
import com.example.homechef.model.CountryModel;
import com.example.homechef.model.Post;
import com.example.homechef.model.User;
import com.example.homechef.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

class PostViewHolder extends RecyclerView.ViewHolder {

    TextView userNameTV;
    TextView titleTV;
    TextView timeTV;
    TextView countryNameTV;
    ImageView userImg;
    ImageView dishImg;
    ImageView countryFlagImageView;
    List<Post> data;
    private final Map<String, String> mCountries = new HashMap<>();

    public PostViewHolder(@NonNull View itemView, PostRecyclerAdapter.OnItemClickListener listener, List<Post> data) {
        super(itemView);
        this.data = data;
        userNameTV = itemView.findViewById(R.id.recipeCardUserName);
        titleTV = itemView.findViewById(R.id.recipeCardTitle);
        timeTV = itemView.findViewById(R.id.recipeCardTime);
        countryNameTV = itemView.findViewById(R.id.recipeCardCountryName);
        userImg = itemView.findViewById(R.id.recipeCardUserPic);
        dishImg = itemView.findViewById(R.id.recipeCardDishImg);
        countryFlagImageView = itemView.findViewById(R.id.countryFlag);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                listener.onItemClick(pos);
            }
        });
        Locale englishLanguage = new Locale.Builder().setLanguage("en").build();
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            mCountries.put(l.getDisplayCountry(englishLanguage), iso);
        }
    }

    public void bind(Post post, int position) {


        titleTV.setText(post.title);
        timeTV.setText(Utils.timeToString(post.getTime()));

        LiveData<Country> data = CountryModel.getInstance().getCountryByName(post.getCountryName());
        String countryCode = mCountries.get(post.countryName);
        data.observeForever(new Observer<Country>() {
            @Override
            public void onChanged(Country country) {
                if (country != null) {
                    Picasso.get().load(country.getFlag().getImageUrlPng()).resize(48, 48)
                            .into(countryFlagImageView);
                    countryNameTV.setText(country.getHebrewName());
                } else {
                    Picasso.get().load("https://flagsapi.com/" + mCountries.get(post.countryName) + "/flat/32.png").fit()
                            .into(countryFlagImageView);
                    Locale countryLocale = new Locale.Builder().setRegion(countryCode).build();
                    Locale hebrewLanguage = new Locale.Builder().setLanguage("he").build();

                    countryNameTV.setText(countryLocale.getDisplayCountry(hebrewLanguage));
                }
                data.removeObserver(this);
            }
        });

        if(!post.getDishImg().isEmpty() && post.getDishImg() != null){
            Picasso.get().load(post.getDishImg()).into(dishImg);
        }

        final User user = post.user;
        userNameTV.setText(user.getUserName());
        if (post.getDishImg() != null && post.getDishImg().length() > 5) {
            Picasso.get().load(post.getDishImg()).placeholder(R.drawable.icon_person).into(userImg);
        } else {
            userImg.setImageResource(R.drawable.icon_person);
        }
    }
}

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostViewHolder> {
    OnItemClickListener listener;

    public PostRecyclerAdapter(LayoutInflater inflater, List<Post> data) {
        this.inflater = inflater;
        this.data = data;
    }
    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    LayoutInflater inflater;
    List<Post> data;

    public void setData(List<Post> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_recipe_card, parent, false);
        return new PostViewHolder(view, listener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.bind(data.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }
}
