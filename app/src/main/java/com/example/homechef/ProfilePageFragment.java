package com.example.homechef;

import android.content.Intent;
import android.os.Bundle;

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

public class ProfilePageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_page, container, false);

        ImageButton imageButton = (ImageButton) view.findViewById(R.id.editButton);
        ImageView imageView = (ImageView) view.findViewById(R.id.profilePicture);
        Button saveButton = (Button) view.findViewById(R.id.saveButton);
        Button cancelButton = (Button) view.findViewById(R.id.cancelButton);
        EditText userNameInput = (EditText) view.findViewById(R.id.userNameInput);

        String email = Model.instance().getConnectedUser();
        System.out.println(email);
        Model.instance().getUserById(email, (user) -> {
            userNameInput.setText(user.getUserName());
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

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivity(intent);
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
                Button saveButton = (Button) view.findViewById(R.id.saveButton);
                saveButton.setVisibility(View.INVISIBLE);
                Button cancelButton = (Button) view.findViewById(R.id.cancelButton);
                cancelButton.setVisibility(View.INVISIBLE);
                EditText editText = (EditText) view.findViewById(R.id.userNameInput);
                editText.setEnabled(false);
                ImageView imageView = (ImageView) view.findViewById(R.id.profilePicture);
                imageView.setClickable(false);
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
        ImageView imageView = (ImageView) v.findViewById(R.id.profilePicture);
        imageView.setClickable(false);
        ImageButton galleryButton = (ImageButton) v.findViewById(R.id.galleryButton);
        galleryButton.setVisibility(View.INVISIBLE);
        ImageButton cameraButton = (ImageButton) v.findViewById(R.id.cameraButton);
        cameraButton.setVisibility(View.INVISIBLE);
    }

    public void saveChanges(View v) {
        EditText userNameInput = (EditText) v.findViewById(R.id.userNameInput);
        String userName = userNameInput.getText().toString().trim();
        String email = Model.instance().getConnectedUser();
        Model.instance().getUserById(email, (user) -> {
            User newUser = new User(user.getEmail(), userName, user.getAvatarUrl());
            Model.instance().editInfo(newUser, (task) -> {
                resetFragment(v);
            });
        });
    }
}