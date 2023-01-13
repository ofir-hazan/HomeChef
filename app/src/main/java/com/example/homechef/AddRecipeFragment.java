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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class AddRecipeFragment extends Fragment {

    private ArrayList<String> countryNames;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Spinner dropdownSpinner = container.findViewById(R.id.countryDropdown);
        getCountries();//function to get and edit json from api
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, countryNames);
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

    private void getCountries(){
        String countriesURL = "https://api.first.org/data/v1/countries";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(countriesURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = new JSONArray(response.getJSONArray("data"));
                    for (int i = 0; i< array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        countryNames.add(jsonObject.getString("country"));
                    }
                } catch (JSONException e) {
                    countryNames = new ArrayList<String>(
                            Arrays.asList("Israel", "Turkey", "Italy", "USA", "UK"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                countryNames = new ArrayList<String>(
                        Arrays.asList("Israel", "Turkey", "Italy", "USA", "UK"));
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjectRequest);
    }
}