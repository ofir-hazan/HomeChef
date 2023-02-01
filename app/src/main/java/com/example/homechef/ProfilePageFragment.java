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

public class ProfilePageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_page, container, false);

        ImageButton imageButton = (ImageButton) view.findViewById(R.id.editButton);
        ImageView imageView = (ImageView) view.findViewById(R.id.profilePicture);
        Button button = (Button) view.findViewById(R.id.saveButton);

        imageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Button button = (Button) view.findViewById(R.id.saveButton);
                button.setVisibility(View.VISIBLE);
                EditText editText = (EditText) view.findViewById(R.id.userNameInput);
                editText.setEnabled(true);
                ImageView imageView = (ImageView) view.findViewById(R.id.profilePicture);
                imageView.setClickable(true);
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) view.findViewById(R.id.saveButton);
                button.setVisibility(View.INVISIBLE);
                EditText editText = (EditText) view.findViewById(R.id.userNameInput);
                editText.setEnabled(false);
                ImageView imageView = (ImageView) view.findViewById(R.id.profilePicture);
                imageView.setClickable(false);
            }
        });

        return view;
    }
}