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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
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
import java.util.Collections;
import java.util.Locale;
import java.util.stream.Collectors;

public class AddRecipeFragment extends Fragment {

    private ArrayList<String> countryNames =new ArrayList<String>();
    private TextView tv;
    private View inf;
    private Spinner dropdownSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        inf = inflater.inflate(R.layout.fragment_add_recipe, container, false);

        dropdownSpinner = (Spinner) inf.findViewById(R.id.countryDropdown);

        useApi(container);

        Button cancelButton = (Button) inf.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> {
            //TODO Do something in response to button click
        });

//        Button saveButton = (Button) container.findViewById(R.id.saveButton);
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // TODO Do something in response to button click
//            }
//        });
//        Button uploadPicButton = (Button) container.findViewById(R.id.uploadPicButton);
//        uploadPicButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // TODO Do something in response to button click
//            }
//        });

        return inf;
    }

    private void useApi(ViewGroup container){
        String countriesURL = "https://laravel-world.com/api/countries";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(countriesURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) { try {
                JSONArray array = response.getJSONArray("data");
                for (int i = 0; i< array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    countryNames.add(jsonObject.getString("name"));
                }

                setSpinner();

            } catch (JSONException e) {
                countryNames = new ArrayList<String>( Arrays.asList("Israel", "Turkey", "Italy", "USA", "UK"));
            }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(container.getContext());
        requestQueue.add(jsonObjectRequest);
    }

    private void setSpinner(){
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
    }
}