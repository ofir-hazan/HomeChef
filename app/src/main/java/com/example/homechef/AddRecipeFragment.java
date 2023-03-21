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

import com.aceinteract.android.stepper.StepperNavListener;
import com.aceinteract.android.stepper.StepperNavigationView;
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

public class AddRecipeFragment extends Fragment implements StepperNavListener {

    private StepperNavigationView stepper;
    private Button prevButton, nextButton;

    public static final int STEPS = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);

        stepper = view.findViewById(R.id.stepper);
        prevButton = view.findViewById(R.id.add_recipe_prev_button);
        nextButton = view.findViewById(R.id.add_recipe_next_button);

        prevButton.setOnClickListener(v -> {
            if(stepper.getCurrentStep() > 0) {
                stepper.goToPreviousStep();
            } else {
                // Todo: Cancel
            }
        });

        nextButton.setOnClickListener(v -> {
            stepper.goToNextStep();
        });

        stepper.setStepperNavListener(this);
        return view;
    }

    @Override
    public void onCompleted() {
        Toast.makeText(getContext(), "Completed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStepChanged(int step) {
        if (step == 0) {
            prevButton.setText(getString(R.string.cancel));
        } else {
            prevButton.setText(getString(R.string.previous));
        }

        if (step == STEPS - 1) {
            nextButton.setText(getString(R.string.finish));
        } else {
            nextButton.setText(getString(R.string.next));
        }
    }
}