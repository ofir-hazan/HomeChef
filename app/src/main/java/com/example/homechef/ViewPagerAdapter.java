package com.example.homechef;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new AddRecipeFragment();
            case 1:
                return null;
            case 3:
                return null;
            default:return  new AddRecipeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
