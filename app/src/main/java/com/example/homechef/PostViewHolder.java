package com.example.homechef;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homechef.model.Post;
import com.example.homechef.model.PostCard;
import com.example.homechef.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostViewHolder extends RecyclerView.ViewHolder {

    TextView userNameTV;
    TextView titleTV;
    TextView timeTV;
    TextView countryNameTV;
    ImageView userImg;
    ImageView dishImg;
    List<PostCard> data;

    public PostViewHolder(@NonNull View itemView, PostRecyclerAdapter.OnItemClickListener listener, List<PostCard> data) {
        super(itemView);
        this.data = data;
        userNameTV = itemView.findViewById(R.id.recipeCardUserName);
        titleTV = itemView.findViewById(R.id.recipeCardTitle);
        timeTV = itemView.findViewById(R.id.recipeCardTime);
        countryNameTV = itemView.findViewById(R.id.recipeCardCountryName);
        userImg = itemView.findViewById(R.id.recipeCardUserPic);
        dishImg = itemView.findViewById(R.id.recipeCardDishImg);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                listener.onItemClick(pos);
            }
        });
    }

    public void bind(PostCard postCard, int position) {
        final Post post = postCard.post;
        final User user = postCard.user;
        userNameTV.setText(user.getUserName());

        if (post.getDishImg() != null && post.getDishImg().length() > 5) {
            Picasso.get().load(post.getDishImg()).placeholder(R.drawable.icon_person).into(dishImg);
        } else {
            dishImg.setImageResource(R.drawable.icon_person);
        }
    }
}

