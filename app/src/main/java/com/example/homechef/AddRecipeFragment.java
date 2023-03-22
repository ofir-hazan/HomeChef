package com.example.homechef;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.aceinteract.android.stepper.StepperNavListener;
import com.aceinteract.android.stepper.StepperNavigationView;
import com.example.homechef.model.Model;
import com.example.homechef.model.Post;

public class AddRecipeFragment extends Fragment implements StepperNavListener {

    private StepperNavigationView stepper;
    private Button prevButton, nextButton;
    private static Post newPost;

    public static final int STEPS = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_recipe, container, false);

        stepper = view.findViewById(R.id.stepper);
        prevButton = view.findViewById(R.id.add_recipe_prev_button);
        nextButton = view.findViewById(R.id.add_recipe_next_button);

        newPost = new Post();

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
        Model.instance().addPost(newPost, (newPost)->{
            stepper.goToNextStep();
            });
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