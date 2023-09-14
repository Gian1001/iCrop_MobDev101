package com.example.icrop_project;

public class OTPData {
    private static OTPData instance;
    private String generatedOTP;

    private OTPData() {
    }

    public static synchronized OTPData getInstance() {
        if (instance == null) {
            instance = new OTPData();
        }
        return instance;
    }

    public String getGeneratedOTP() {
        return generatedOTP;
    }

    public void setGeneratedOTP(String generatedOTP) {
        this.generatedOTP = generatedOTP;
    }
}
