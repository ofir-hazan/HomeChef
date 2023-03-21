package com.example.homechef;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.homechef.model.Country;
import com.example.homechef.model.CountryModel;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddRecipeDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddRecipeDetails extends Fragment {

    private MaterialAutoCompleteTextView countriesAutocompleteTextView;
    private final ArrayList<String> countries = new ArrayList<>();

    public AddRecipeDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddRecipeDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static AddRecipeDetails newInstance(String param1, String param2) {
        return new AddRecipeDetails();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_recipe_details, container, false);

        countriesAutocompleteTextView = view.findViewById(R.id.countryDropdown);

        useApi(container);

//        Button uploadPicButton = (Button) container.findViewById(R.id.uploadPicButton);
//        uploadPicButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // TODO Do something in response to button click
//            }
//        });

        return view;
    }

    private void useApi(ViewGroup container) {
        CountryModel.getInstance().getAllCountries().observe(getViewLifecycleOwner(), countries -> {
            this.countries.clear();

            for(Country country : countries) {
                this.countries.add(country.getHebrewName());
            }

            countriesAutocompleteTextView.setSimpleItems(this.countries.toArray(new String[0]));
        });
    }

    private void setSpinner() {
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, countryNames);
        countriesAutocompleteTextView.showDropDown();
        System.out.println("show drop down");
        System.out.println(countries);
        System.out.println(countriesAutocompleteTextView);
        countriesAutocompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getActivity(), "You Selected: " + adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();  // test
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}