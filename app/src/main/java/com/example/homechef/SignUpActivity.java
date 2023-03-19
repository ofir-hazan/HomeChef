package com.example.homechef;

import static com.example.homechef.utils.Utils.patternMatches;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private EditText userNameInput, emailInput, passwordInput;
    private Button signUpButton;
    private ImageView uploadProfilePicture;
    private ImageButton galleryButton, cameraButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        ActivityResultLauncher<Void> cameraLauncher;
        ActivityResultLauncher<String> galleryLauncher;

        uploadProfilePicture = (ImageView) findViewById(R.id.uploadProfilePicture);

        userNameInput = (EditText) findViewById(R.id.userNameInput);
        emailInput = (EditText) findViewById(R.id.emailInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);

        signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpUser();
            }
        });

        cameraButton = (ImageButton) findViewById(R.id.cameraButton);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    uploadProfilePicture.setImageBitmap(result);
                }
            }
        });

        cameraButton.setOnClickListener(view1->{
            cameraLauncher.launch(null);
        });

        galleryButton = (ImageButton) findViewById(R.id.galleryButton);

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null){
                    uploadProfilePicture.setImageURI(result);
                }
            }
        });

        galleryButton.setOnClickListener(view1->{
            galleryLauncher.launch("image/*");
        });
    }

    private void signUpUser() {
        String userName = userNameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (userName == "" || email == "" || password == "") {
            Toast.makeText(SignUpActivity.this, "אנא מלאו את כל השדות", Toast.LENGTH_LONG).show();
            return;
        }

        if (!patternMatches(email)) {
            Toast.makeText(SignUpActivity.this, "אנא הכניסו אימייל תקין", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // TODO: save user data in the database
                        FirebaseUser user = mAuth.getCurrentUser();
                        navToActivity();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Sign up failed, please try again" + task.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });
    }

    private void navToActivity() {
        Intent navToActivityIntent = new Intent(this, MainActivity.class);
        startActivity(navToActivityIntent);
    }
}