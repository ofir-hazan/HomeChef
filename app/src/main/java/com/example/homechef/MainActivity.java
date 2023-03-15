package com.example.homechef;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    NavController navController;
    private FirebaseAuth mAuth;
    private List<Post> fakeData = new LinkedList<Post>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fakeData.add( new Post("פנקייקים",
                "Ofir",
                "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.delscookingtwist.com%2Fwp-content%2Fuploads%2F2022%2F01%2FEasy-Fluffy-American-Pancakes_1.jpg&imgrefurl=https%3A%2F%2Fwww.delscookingtwist.com%2Feasy-fluffy-american-pancakes%2F&tbnid=cBvvx2NllyJqmM&vet=12ahUKEwio05Hypt78AhVkoScCHVaIChwQMygBegUIARDsAQ..i&docid=skzL-P2SG2WeQM&w=1200&h=1800&q=pancake&ved=2ahUKEwio05Hypt78AhVkoScCHVaIChwQMygBegUIARDsAQ",
                "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.delscookingtwist.com%2Fwp-content%2Fuploads%2F2022%2F01%2FEasy-Fluffy-American-Pancakes_1.jpg&imgrefurl=https%3A%2F%2Fwww.delscookingtwist.com%2Feasy-fluffy-american-pancakes%2F&tbnid=cBvvx2NllyJqmM&vet=12ahUKEwio05Hypt78AhVkoScCHVaIChwQMygBegUIARDsAQ..i&docid=skzL-P2SG2WeQM&w=1200&h=1800&q=pancake&ved=2ahUKEwio05Hypt78AhVkoScCHVaIChwQMygBegUIARDsAQ",
                "Israel", "time"));
        fakeData.add( new Post("פנקייקים",
                "Ofir",
                "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.delscookingtwist.com%2Fwp-content%2Fuploads%2F2022%2F01%2FEasy-Fluffy-American-Pancakes_1.jpg&imgrefurl=https%3A%2F%2Fwww.delscookingtwist.com%2Feasy-fluffy-american-pancakes%2F&tbnid=cBvvx2NllyJqmM&vet=12ahUKEwio05Hypt78AhVkoScCHVaIChwQMygBegUIARDsAQ..i&docid=skzL-P2SG2WeQM&w=1200&h=1800&q=pancake&ved=2ahUKEwio05Hypt78AhVkoScCHVaIChwQMygBegUIARDsAQ",
                "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.delscookingtwist.com%2Fwp-content%2Fuploads%2F2022%2F01%2FEasy-Fluffy-American-Pancakes_1.jpg&imgrefurl=https%3A%2F%2Fwww.delscookingtwist.com%2Feasy-fluffy-american-pancakes%2F&tbnid=cBvvx2NllyJqmM&vet=12ahUKEwio05Hypt78AhVkoScCHVaIChwQMygBegUIARDsAQ..i&docid=skzL-P2SG2WeQM&w=1200&h=1800&q=pancake&ved=2ahUKEwio05Hypt78AhVkoScCHVaIChwQMygBegUIARDsAQ",
                "Israel", "time"));
        fakeData.add( new Post("פנקייקים",
                "Ofir",
                "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.delscookingtwist.com%2Fwp-content%2Fuploads%2F2022%2F01%2FEasy-Fluffy-American-Pancakes_1.jpg&imgrefurl=https%3A%2F%2Fwww.delscookingtwist.com%2Feasy-fluffy-american-pancakes%2F&tbnid=cBvvx2NllyJqmM&vet=12ahUKEwio05Hypt78AhVkoScCHVaIChwQMygBegUIARDsAQ..i&docid=skzL-P2SG2WeQM&w=1200&h=1800&q=pancake&ved=2ahUKEwio05Hypt78AhVkoScCHVaIChwQMygBegUIARDsAQ",
                "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.delscookingtwist.com%2Fwp-content%2Fuploads%2F2022%2F01%2FEasy-Fluffy-American-Pancakes_1.jpg&imgrefurl=https%3A%2F%2Fwww.delscookingtwist.com%2Feasy-fluffy-american-pancakes%2F&tbnid=cBvvx2NllyJqmM&vet=12ahUKEwio05Hypt78AhVkoScCHVaIChwQMygBegUIARDsAQ..i&docid=skzL-P2SG2WeQM&w=1200&h=1800&q=pancake&ved=2ahUKEwio05Hypt78AhVkoScCHVaIChwQMygBegUIARDsAQ",
                "Israel", "time"));
        fakeData.add( new Post("פנקייקים",
                "Ofir",
                "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.delscookingtwist.com%2Fwp-content%2Fuploads%2F2022%2F01%2FEasy-Fluffy-American-Pancakes_1.jpg&imgrefurl=https%3A%2F%2Fwww.delscookingtwist.com%2Feasy-fluffy-american-pancakes%2F&tbnid=cBvvx2NllyJqmM&vet=12ahUKEwio05Hypt78AhVkoScCHVaIChwQMygBegUIARDsAQ..i&docid=skzL-P2SG2WeQM&w=1200&h=1800&q=pancake&ved=2ahUKEwio05Hypt78AhVkoScCHVaIChwQMygBegUIARDsAQ",
                "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.delscookingtwist.com%2Fwp-content%2Fuploads%2F2022%2F01%2FEasy-Fluffy-American-Pancakes_1.jpg&imgrefurl=https%3A%2F%2Fwww.delscookingtwist.com%2Feasy-fluffy-american-pancakes%2F&tbnid=cBvvx2NllyJqmM&vet=12ahUKEwio05Hypt78AhVkoScCHVaIChwQMygBegUIARDsAQ..i&docid=skzL-P2SG2WeQM&w=1200&h=1800&q=pancake&ved=2ahUKEwio05Hypt78AhVkoScCHVaIChwQMygBegUIARDsAQ",
                "Israel", "time"));
        fakeData.add( new Post("פנקייקים",
                "Ofir",
                "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.delscookingtwist.com%2Fwp-content%2Fuploads%2F2022%2F01%2FEasy-Fluffy-American-Pancakes_1.jpg&imgrefurl=https%3A%2F%2Fwww.delscookingtwist.com%2Feasy-fluffy-american-pancakes%2F&tbnid=cBvvx2NllyJqmM&vet=12ahUKEwio05Hypt78AhVkoScCHVaIChwQMygBegUIARDsAQ..i&docid=skzL-P2SG2WeQM&w=1200&h=1800&q=pancake&ved=2ahUKEwio05Hypt78AhVkoScCHVaIChwQMygBegUIARDsAQ",
                "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.delscookingtwist.com%2Fwp-content%2Fuploads%2F2022%2F01%2FEasy-Fluffy-American-Pancakes_1.jpg&imgrefurl=https%3A%2F%2Fwww.delscookingtwist.com%2Feasy-fluffy-american-pancakes%2F&tbnid=cBvvx2NllyJqmM&vet=12ahUKEwio05Hypt78AhVkoScCHVaIChwQMygBegUIARDsAQ..i&docid=skzL-P2SG2WeQM&w=1200&h=1800&q=pancake&ved=2ahUKEwio05Hypt78AhVkoScCHVaIChwQMygBegUIARDsAQ",
                "Israel", "time"));

        mAuth = FirebaseAuth.getInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment)  fragmentManager.findFragmentById(R.id.main_navhost);
        navController = navHostFragment.getNavController();
        fragmentManager.beginTransaction().replace(R.id.main_navhost, RecipeListFragment.class, null).setReorderingAllowed(true).commit();
        NavigationUI.setupActionBarWithNavController(this, navController);



        BottomNavigationView navView = findViewById(R.id.main_bottomNavigationView);
        NavigationUI.setupWithNavController(navView, navController);
    }
}