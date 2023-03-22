package com.example.homechef;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homechef.model.Post;
import com.example.homechef.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

class PostViewHolder extends RecyclerView.ViewHolder {

    TextView userNameTV;
    TextView titleTV;
    TextView timeTV;
    TextView countryNameTV;
    ImageView userImg;
    ImageView dishImg;
    List<Post> data;

    public PostViewHolder(@NonNull View itemView, PostRecyclerAdapter.OnItemClickListener listener, List<Post> data) {
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
                int pos = getAbsoluteAdapterPosition();
                System.out.println(pos);
                listener.onItemClick(pos);
            }
        });
    }

    public void bind(Post post, int position) {
        final User user = post.user;
        userNameTV.setText(user.getUserName());

        if (post.getDishImg() != null && post.getDishImg().length() > 5) {
            Picasso.get().load(post.getDishImg()).placeholder(R.drawable.icon_person).into(dishImg);
        } else {
            dishImg.setImageResource(R.drawable.icon_person);
        }
    }
}

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostViewHolder> {
    OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    LayoutInflater inflater;
    List<Post> data;

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Post> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public PostRecyclerAdapter(LayoutInflater inflater, List<Post> data) {
        this.inflater = inflater;
        this.data = data;
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
