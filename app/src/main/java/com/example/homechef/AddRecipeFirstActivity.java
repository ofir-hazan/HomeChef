package com.example.homechef;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class AddRecipeFirstActivity extends AppCompatActivity {

    private final String defaultCountryNames[] = {"Israel", "Turkey", "Italy", "USA", "UK"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe_first);

        Spinner spinner = findViewById(R.id.countryDropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddRecipeFirstActivity.this, android.R.layout.simple_spinner_dropdown_item, defaultCountryNames);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(AddRecipeFirstActivity.this, "you selected:"+adapterView.getItemAtPosition(position),Toast.LENGTH_SHORT).show();  // test
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
            }
        });
        Button continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
            }
        });
        Button uploadPicButton = (Button) findViewById(R.id.uploadPicButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
            }
        });

    }
}