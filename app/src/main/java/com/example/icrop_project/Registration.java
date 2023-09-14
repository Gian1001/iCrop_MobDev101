package com.example.icrop_project;

import static com.example.icrop_project.OTPpage.generateOTP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import android.widget.ProgressBar;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Registration extends AppCompatActivity {

    public TextInputEditText editTextEmail, editTextPassword, editContactValue;
    Button buttonReg;
    FirebaseAuth auth;
    ProgressBar progressBar;
    TextView textView;
    private static final int OTP_REQUEST_CODE = 1;


    @Override
    public void onStart() {
        super.onStart();
        // Check if the user is signed in (non-null) and update the UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonReg = findViewById(R.id.btn_register);
        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.loginNow);
        editContactValue = findViewById(R.id.contact);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());
                boolean isButtonPressed = getIntent().getBooleanExtra("otpVerification", false);


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Registration.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Registration.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                SendMessageTask sendMessageTask = new SendMessageTask();
                sendMessageTask.execute(generateOTP());

                Intent otpIntent = new Intent(Registration.this, OTPpage.class);
                startActivityForResult(otpIntent, OTP_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OTP_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                boolean isButtonPressed = data.getBooleanExtra("otpVerification", false);

                if (isButtonPressed) {
                    pushInputs();
                    String email = String.valueOf(editTextEmail.getText());
                    String password = String.valueOf(editTextPassword.getText());
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        // Registration success
                                        Toast.makeText(Registration.this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                                        // Go to the login page
                                        finish();
                                    } else {
                                        // Registration failed
                                        Toast.makeText(Registration.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {

                }
            }
        }
    }


    public static String generateUserId() {
        String uniqueId = UUID.randomUUID().toString();

        uniqueId = uniqueId.replace("-", "");

        return uniqueId;
    }

    private void pushInputs() {
        String userID = generateUserId();


        Map<String, Object> reportMap = new HashMap<>();
        reportMap.put("userID", userID);
        reportMap.put("userEmail", editTextEmail.getText().toString());
        reportMap.put("userContact", editContactValue.getText().toString());


        FirebaseDatabase.getInstance().getReference().child("userData").child(userID).setValue(reportMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent intent = new Intent(Registration.this, MainActivity.class); // Use CropPlanting.this as the first argument
                        startActivity(intent);
                        finish();
                    }
                });
    }
}
