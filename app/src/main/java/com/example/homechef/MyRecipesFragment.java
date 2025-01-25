package com.example.homechef;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homechef.adapters.PostRecyclerAdapter;
import com.example.homechef.databinding.FragmentMyRecipesBinding;
import com.example.homechef.model.Post;

public class MyRecipesFragment extends Fragment {
    FragmentMyRecipesBinding binding;
    PostRecyclerAdapter adapter;
    PostsListFragmentViewModel viewModel;

    public MyRecipesFragment() {
    }

    public static MyRecipesFragment newInstance(int columnCount) {
        MyRecipesFragment fragment = new MyRecipesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyRecipesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostRecyclerAdapter(getLayoutInflater(),viewModel.getUserPostsData().getValue());
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new PostRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Log.d("TAG", "Row was clicked " + pos);

                Post post = viewModel.getUserPostsData().getValue().get(pos);

                MyRecipesFragmentDirections.ActionMyRecipesFragmentToDisplayEditRecipeFragment action = MyRecipesFragmentDirections.actionMyRecipesFragmentToDisplayEditRecipeFragment(post.getId());
                Navigation.findNavController(view).navigate(action);
            }
        });

        viewModel.getUserPostsData().observe(getViewLifecycleOwner(),list->{
            adapter.setData(list);
        });
        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(PostsListFragmentViewModel.class);
    }
}