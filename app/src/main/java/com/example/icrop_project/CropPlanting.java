package com.example.icrop_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CropPlanting extends AppCompatActivity {
/*works pero wala pa validation especially sa date
to add din yung editText for others,
then dialog once oks na yung account
*/

    private DatePickerDialog datePickerDialog;
    private Button dateButton, harvestButton, submitButton, addImageButton;
    private ImageView getImage;
    private Spinner spinnerCrops;
    private String selectedCropType;
    private int selectedButtonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_planting);

        // Initialize views and set adapters
        initializeViews();

        // Initialize date picker
        initDatePicker(R.id.plantingButton);
        initDatePicker(R.id.harvestDate);
    }

    private void initializeViews() {
        // Initialize and set listeners for buttons
        dateButton = findViewById(R.id.plantingButton);
        dateButton.setText(getTodaysDate());

        harvestButton = findViewById(R.id.harvestDate);
        harvestButton.setText(getTodaysDate());

        submitButton = findViewById(R.id.submitPlantReport);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushInputs();
            }
        });

        // Initialize spinner and set its adapter and listener
        spinnerCrops = findViewById(R.id.cropType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.dropdown_options, android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCrops.setAdapter(adapter);
        spinnerCrops.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) {
                selectedCropType = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void pushInputs() {
        Map<String, Object> reportMap = new HashMap<>();
        reportMap.put("CropType", selectedCropType);
        reportMap.put("datePlanted", harvestButton.getText().toString());
        reportMap.put("dateHarvest", dateButton.getText().toString());

        String reportId = generateReportId();

        FirebaseDatabase.getInstance().getReference().child("CropPlanner").child(reportId).setValue(reportMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent intent = new Intent(CropPlanting.this, MainActivity.class); // Use CropPlanting.this as the first argument
                        startActivity(intent);
                        finish();
                    }
                });
    }

    private void initDatePicker(final int buttonId) {
        final Button dateButton = findViewById(buttonId);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
                selectedButtonId = buttonId;
            }
        });

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);

                Button selectedButton = findViewById(selectedButtonId);
                selectedButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        if (month >= 1 && month <= 12) {
            return months[month - 1] + " " + day + " " + year;
        }
        return "Jan" + " " + day + " " + year;
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    public static String generateReportId() {

        //fix format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());

        Random random = new Random();
        int randomNumber = random.nextInt(10000); // You can adjust the range as needed

        return timestamp + String.format("%04d", randomNumber);
    }
}