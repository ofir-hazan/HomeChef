package com.example.homechef;

import static com.example.homechef.utils.Utils.patternMatches;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homechef.model.Model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button logInButton;
    private TextView signUpFrom;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            navToActivity(MainActivity.class);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();
        emailInput = (EditText) findViewById(R.id.emailInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);

        logInButton = (Button) findViewById(R.id.logInButton);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInUser();
            }
        });

        signUpFrom = (TextView) findViewById(R.id.signUpFrom);
        signUpFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navToActivity(SignUpActivity.class);
            }
        });
    }

    private void logInUser() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email == "" || password == "") {
            Toast.makeText(LogInActivity.this, "אנא מלאו את כל השדות", Toast.LENGTH_LONG).show();
            return;
        }

        if (!patternMatches(email)) {
            Toast.makeText(LogInActivity.this, "אנא הכניסו אימייל תקין", Toast.LENGTH_LONG).show();
            return;
        }

        Model.instance().login(email, password, (user) -> {
            navToActivity(MainActivity.class);
        });
    }

    private void navToActivity(Class activityClass) {
        Intent navToActivityIntent = new Intent(this, activityClass);
        startActivity(navToActivityIntent);
    }
}