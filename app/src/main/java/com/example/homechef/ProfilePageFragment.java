package com.example.homechef;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.homechef.model.Model;
import com.example.homechef.model.User;
import com.squareup.picasso.Picasso;

public class ProfilePageFragment extends Fragment {

    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_page, container, false);

        ImageButton imageButton = (ImageButton) view.findViewById(R.id.editButton);
        ImageView imageView = (ImageView) view.findViewById(R.id.profilePicture);
        ImageButton galleryButton = (ImageButton) view.findViewById(R.id.galleryButton);
        ImageButton cameraButton = (ImageButton) view.findViewById(R.id.cameraButton);
        Button saveButton = (Button) view.findViewById(R.id.saveButton);
        Button cancelButton = (Button) view.findViewById(R.id.cancelButton);
        EditText userNameInput = (EditText) view.findViewById(R.id.userNameInput);

        // Init the profile data
        String email = Model.instance().getConnectedUser();
        Model.instance().getUserById(email, (user) -> {
            userNameInput.setText(user.getUserName());
            if (user.getAvatarUrl() != null && !user.getAvatarUrl().equals("")) {
                Picasso.get()
                        .load(user.getAvatarUrl())
                        .into(imageView);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Button saveButton = (Button) view.findViewById(R.id.saveButton);
                saveButton.setVisibility(View.VISIBLE);
                Button cancelButton = (Button) view.findViewById(R.id.cancelButton);
                cancelButton.setVisibility(View.VISIBLE);
                EditText userNameInput = (EditText) view.findViewById(R.id.userNameInput);
                userNameInput.setEnabled(true);
                ImageButton galleryButton = (ImageButton) view.findViewById(R.id.galleryButton);
                galleryButton.setVisibility(View.VISIBLE);
                ImageButton cameraButton = (ImageButton) view.findViewById(R.id.cameraButton);
                cameraButton.setVisibility(View.VISIBLE);
            }
        });

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null){
                    imageView.setImageURI(result);
                }
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryLauncher.launch("image/*");
            }
        });

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    imageView.setImageBitmap(result);
                }
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraLauncher.launch(null);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges(view);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFragment(view);
            }
        });

        return view;
    }

    private void resetFragment(View v) {
        Button saveButton = (Button) v.findViewById(R.id.saveButton);
        saveButton.setVisibility(View.INVISIBLE);
        Button cancelButton = (Button) v.findViewById(R.id.cancelButton);
        cancelButton.setVisibility(View.INVISIBLE);
        EditText userNameInput = (EditText) v.findViewById(R.id.userNameInput);
        userNameInput.setEnabled(false);
        ImageButton galleryButton = (ImageButton) v.findViewById(R.id.galleryButton);
        galleryButton.setVisibility(View.INVISIBLE);
        ImageButton cameraButton = (ImageButton) v.findViewById(R.id.cameraButton);
        cameraButton.setVisibility(View.INVISIBLE);
    }

    public void saveChanges(View v) {
        EditText userNameInput = (EditText) v.findViewById(R.id.userNameInput);
        String userName = userNameInput.getText().toString().trim();

        ImageView profilePicture = (ImageView) v.findViewById(R.id.profilePicture);
        profilePicture.setDrawingCacheEnabled(true);
        profilePicture.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) profilePicture.getDrawable()).getBitmap();

        String email = Model.instance().getConnectedUser();
        User newUser = new User(email, userName, "");
        Model.instance().getUserById(email, (user) -> {
            newUser.setAvatarUrl(user.getAvatarUrl());
        });

        Model.instance().uploadImage(email, bitmap, url-> {
            if (url != null){
                newUser.setAvatarUrl(url);
            }

            Model.instance().editInfo(newUser, (task) -> {
                resetFragment(v);
            });
        });
    }
}