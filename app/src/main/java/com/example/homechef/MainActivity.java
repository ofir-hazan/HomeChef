package com.example.homechef;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    AddRecipeFragment addRecipeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabs = findViewById(R.id.navFooter);
        ViewPager viewPager = findViewById(R.id.fragmentContainer);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

//        bottomNavigationView = findViewById(R.id.navFooter);
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, addRecipeFragment).commit();
    }
}