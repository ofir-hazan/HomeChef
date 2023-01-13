package com.example.homechef;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class AddRecipeFragment extends Fragment {
    private final String defaultCountryNames[] = {"Israel", "Turkey", "Italy", "USA", "UK"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Spinner dropdownSpinner = container.findViewById(R.id.countryDropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, defaultCountryNames);
        dropdownSpinner.setAdapter(adapter);
        dropdownSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getActivity(), "you selected:"+adapterView.getItemAtPosition(position),Toast.LENGTH_SHORT).show();  // test
                //TODO add action
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button cancelButton = (Button) container.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO Do something in response to button click
            }
        });
        Button saveButton = (Button) container.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Do something in response to button click
            }
        });
        Button uploadPicButton = (Button) container.findViewById(R.id.uploadPicButton);
        uploadPicButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Do something in response to button click
            }
        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_recipe, container, false);
    }
}