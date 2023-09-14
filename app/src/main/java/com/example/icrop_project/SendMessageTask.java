package com.example.icrop_project;

import static com.example.icrop_project.OTPpage.generateOTP;

import android.os.AsyncTask;
import android.util.Log;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SendMessageTask extends AsyncTask<String, Void, String>  {

    @Override
    protected String doInBackground(String... params) {
        String apiUrl = "https://semaphore.co/api/v4/otp";
        String apiKey = "bdc45e3a5086805d089c6ba42acdb667";
        String otpMessage = "NEVER SHARE YOUR OTP especially on social media and SMS or email links. ";
        String otpValue =  params[0];
        String phoneNumber = params[1];


        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);

            String postData = "apikey=" + apiKey + "&number=" + phoneNumber + "&message=" + otpMessage + "&code=" + otpValue;

            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            osw.write(postData);
            osw.flush();
            osw.close();

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return response.toString();
            } else {
                return "HTTP Error: " + responseCode;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        // Handle the result here (e.g., display it in a TextView or log it)
        // You can access the result string with 'result'.
        Log.d("SendMessageTask", "Result: " + result);

    }
}