package com.example.homechef;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.homechef.model.Country;
import com.example.homechef.model.CountryModel;
import com.example.homechef.model.Model;
import com.example.homechef.model.Post;
import com.example.homechef.model.User;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.ArrayList;
import java.util.UUID;

public class AddNewRecipeFragment extends Fragment {

    private MaterialAutoCompleteTextView countriesAutocompleteTextView;
    private final ArrayList<String> countries = new ArrayList<>();
    private Button saveButton, cancelButton;
    private ImageButton galleryButton, cameraButton;
    private ImageView newPostImg;
    private EditText titleText, timeText, fullRecipeText;

    private static Post newPost;

    public AddNewRecipeFragment() {
        // Required empty public constructor
    }

    public static AddNewRecipeFragment newInstance() {
        AddNewRecipeFragment fragment = new AddNewRecipeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_add_new_recipe, container, false);
        countriesAutocompleteTextView = view.findViewById(R.id.countryDropdown);
        useApi(container);
        saveButton=view.findViewById(R.id.save_recipe_button);
        saveButton.setOnClickListener(v->{
            onSave( container);
        });
        cancelButton=view.findViewById(R.id.cancel_recipe_button);
        cancelButton.setOnClickListener(v->{
            navToMainFragment();
        });
        titleText=view.findViewById(R.id.recipeTitle);
        timeText=view.findViewById(R.id.estimatedTime);
        fullRecipeText=view.findViewById(R.id.fullRecipe);
        newPostImg = view.findViewById(R.id.newPostImg);
        cameraButton = view.findViewById(R.id.cameraButton);
        galleryButton = view.findViewById(R.id.galleryButton);
        handleAddPicButtons();

        return view;
    }

    private void handleAddPicButtons(){
        ActivityResultLauncher<Void> cameraLauncher;
        ActivityResultLauncher<String> galleryLauncher;

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    newPostImg.setImageBitmap(result);
                }
            }
        });

        cameraButton.setOnClickListener(view1->{
            cameraLauncher.launch(null);
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null){
                    newPostImg.setImageURI(result);
                }
            }
        });

        galleryButton.setOnClickListener(view1->{
            galleryLauncher.launch("image/*");
        });
    }

    private void useApi(ViewGroup container) {
        CountryModel.getInstance().getAllCountries().observe(getViewLifecycleOwner(), countries -> {
            this.countries.clear();

            for(Country country : countries) {
                this.countries.add(country.getName());
            }

            countriesAutocompleteTextView.setSimpleItems(this.countries.toArray(new String[0]));
        });
    }


    public void onSave(ViewGroup container) {
        String title = titleText.getText().toString().trim();
        String fullRecipe = fullRecipeText.getText().toString().trim();

        if(title.equals("")  || fullRecipe.equals("")){
            Toast.makeText(container.getContext(), "אנא מלאו את הכותרת ותיאור המתכון", Toast.LENGTH_LONG).show();
            return;        }

        User connectedUser = new User();
         Model.instance().getUserById(Model.instance().getConnectedUser(), (user) -> { connectedUser.setEmail(user.getEmail());
             connectedUser.setUserName(user.userName);
             connectedUser.setAvatarUrl(user.avatarUrl);
             newPost = new Post(UUID.randomUUID().toString(),
                     title,
                     connectedUser,
                     "",
                     countriesAutocompleteTextView.getText().toString(),
                     Long.parseLong(timeText.getText().toString()),
                     fullRecipe);
             newPostImg.setDrawingCacheEnabled(true);
             newPostImg.buildDrawingCache();
             Bitmap bitmap = ((BitmapDrawable) newPostImg.getDrawable()).getBitmap();
             Model.instance().uploadImage(newPost.getId(), bitmap, url-> {
                 if (url != null){
                     newPost.dishImg = url;
                 }
                 Model.instance().addPost(newPost, (newPost)->{
                     navToMainFragment();
                 });
             });
        });
    }

    private void navToMainFragment() {
        NavHostFragment finalHost = NavHostFragment.create(R.navigation.nav_graph);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.main_navhost, finalHost)
                .setPrimaryNavigationFragment(finalHost) // equivalent to app:defaultNavHost="true"
                .commit();
    }
}