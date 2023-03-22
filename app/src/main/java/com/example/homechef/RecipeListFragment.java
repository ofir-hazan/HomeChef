package com.example.homechef;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.homechef.databinding.FragmentRecipeListBinding;
import com.example.homechef.model.Model;
import com.example.homechef.model.Post;

public class RecipeListFragment extends Fragment {
        FragmentRecipeListBinding binding;
        PostRecyclerAdapter adapter;
        PostsListFragmentViewModel viewModel;

        public RecipeListFragment() {
        }

        public static RecipeListFragment newInstance(int columnCount) {
                RecipeListFragment fragment = new RecipeListFragment();
                return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                        Bundle savedInstanceState) {

                binding = FragmentRecipeListBinding.inflate(inflater, container, false);
                View view = binding.getRoot();
                binding.recyclerView.setHasFixedSize(true);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new PostRecyclerAdapter(getLayoutInflater(),viewModel.getData().getValue());
                binding.recyclerView.setAdapter(adapter);

                adapter.setOnItemClickListener(new PostRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int pos) {
                                Log.d("TAG", "Row was clicked " + pos);

                                Post post = viewModel.getData().getValue().get(pos);

                                RecipeListFragmentDirections.ActionRecipeListFragmentToDisplayEditRecipeFragment action = RecipeListFragmentDirections.actionRecipeListFragmentToDisplayEditRecipeFragment(post.getId());
                                Navigation.findNavController(view).navigate(action);

                        }
                });

                viewModel.getData().observe(getViewLifecycleOwner(),list->{
                        adapter.setData(list);
                });

                Model.instance().EventPostsListLoadingState.observe(getViewLifecycleOwner(),status->{
                        binding.swipeRefresh.setRefreshing(status == Model.LoadingState.LOADING);
                });

                binding.swipeRefresh.setOnRefreshListener(()->{
                        reloadData();
                });

                return view;
        }

        @Override
        public void onAttach(@NonNull Context context) {
                super.onAttach(context);
                viewModel = new ViewModelProvider(this).get(PostsListFragmentViewModel.class);
        }

        void reloadData(){
                Model.instance().refreshAllPosts();
        }
}