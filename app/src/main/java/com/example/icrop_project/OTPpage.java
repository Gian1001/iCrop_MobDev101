package com.example.icrop_project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import com.example.icrop_project.SendMessageTask;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class OTPpage extends AppCompatActivity {

    TextView textbox;
    private Button button;
    int currentHour, currentMinute, currentSecond;

    Calendar calendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otppage);

        textbox = findViewById(R.id.testBox);
        button = findViewById(R.id.testButton);
        currentHour = calendar.get(Calendar.HOUR_OF_DAY); // 24-hour format
        currentMinute = calendar.get(Calendar.MINUTE);
        currentSecond = calendar.get(Calendar.SECOND);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMessageTask sendMessageTask = new SendMessageTask();
                sendMessageTask.execute(generateOTP());
            }
        });



    }
    public static String generateOTP() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int randomDigit = secureRandom.nextInt(10); // Generate a random digit (0-9)
            otp.append(randomDigit); // Append the digit to the OTP
        }

        return otp.toString();
    }

    private void insertData() {

//        FirebaseDatabase.getInstance().getReference().child("crops_db").setValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//
//            }
//
//        });
    }
}