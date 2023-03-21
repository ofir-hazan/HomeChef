package com.example.homechef;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homechef.model.PostCard;

import java.util.List;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostViewHolder> {
    OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    LayoutInflater inflater;
    List<PostCard> data;

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<PostCard> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public PostRecyclerAdapter(LayoutInflater inflater, List<PostCard> data) {
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
