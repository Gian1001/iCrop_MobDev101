package com.example.icrop_project;

import static com.example.icrop_project.Registration.generateUserId;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import com.example.icrop_project.SendMessageTask;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OTPpage extends AppCompatActivity {


    private Button button;
    int currentHour, currentMinute, currentSecond;
    private boolean isButtonPressed = false;

    private EditText[] editTexts = new EditText[6];
    private int currentEditText = 0;
    EditText firstDigit, secondDigit, thirdDigit, fourthDigit, fifthDigit, sixthDigit;

    private String concatDigits, OTPValue;


    Calendar calendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otppage);

        firstDigit = findViewById(R.id.firstNumber);
        secondDigit = findViewById(R.id.secondNumber);
        thirdDigit = findViewById(R.id.thirdNumber);
        fourthDigit = findViewById(R.id.fourthNumber);
        fifthDigit = findViewById(R.id.fifthNumber);
        sixthDigit = findViewById(R.id.sixthNumber);

        button = findViewById(R.id.testButton);
        currentHour = calendar.get(Calendar.HOUR_OF_DAY); // 24-hour format
        currentMinute = calendar.get(Calendar.MINUTE);
        currentSecond = calendar.get(Calendar.SECOND);

        editTexts[0] = findViewById(R.id.firstNumber);
        editTexts[1] = findViewById(R.id.secondNumber);
        editTexts[2] = findViewById(R.id.thirdNumber);
        editTexts[3] = findViewById(R.id.fourthNumber);
        editTexts[4] = findViewById(R.id.fifthNumber);
        editTexts[5] = findViewById(R.id.sixthNumber);

        // Attach TextWatcher to each EditText
        for (int i = 0; i < editTexts.length; i++) {
            attachTextWatcher(editTexts[i], i);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Concatenate the digits from all EditText fields
                concatDigits = firstDigit.getText().toString() + secondDigit.getText().toString() + thirdDigit.getText().toString() +
                        fourthDigit.getText().toString() + fifthDigit.getText().toString() + sixthDigit.getText().toString();


                String generatedOTP = OTPData.getInstance().getGeneratedOTP();

                if (concatDigits.equals(generatedOTP)) {
                    // OTP is correct, proceed with registration
                    isButtonPressed = true;  // Change the value of the boolean variable

                    // Create an intent to pass the value back to the registration page
                    Intent intent = new Intent();
                    intent.putExtra("otpVerification", isButtonPressed);

                    // Set the result code and the intent with data
                    setResult(RESULT_OK, intent);

                    // Close the OTP page and go back to the registration page
                    finish();
                } else {
                    // OTP is incorrect, show an error message
                    Toast.makeText(OTPpage.this, "Incorrect OTP, please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
    public static String generateOTP() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int randomDigit = secureRandom.nextInt(10);
            otp.append(randomDigit);
        }

        return otp.toString();
    }

    private void attachTextWatcher(final EditText editText, final int index) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // When a digit is entered, automatically focus on the next EditText
                if (editable.length() == 1 && index < editTexts.length - 1) {
                    currentEditText = index + 1;
                    editTexts[currentEditText].requestFocus();
                }
            }
        });
    }
}


