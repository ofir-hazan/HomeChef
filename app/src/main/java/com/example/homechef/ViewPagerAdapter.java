package com.example.homechef;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ProfilePageFragment();
            case 1:
                return new AddRecipeFragment();
            case 3:
                return new HomePageFragment();
            default:return  new HomePageFragment();
    }}

    @Override
    public int getCount() {
        return 4;
    }

//    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
//        super(fragmentActivity);
//    }
//
//    @NonNull
//    @Override
//    public Fragment createFragment(int position) {
//        switch (position){
//            case 0:
//                return new ProfilePageFragment();
//            case 1:
//                return new AddRecipeFragment();
//            case 3:
//                return new HomePageFragment();
//            default:return  new HomePageFragment();
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
}
