package com.example.icrop_project;

import static com.example.icrop_project.CropPlanting.generateReportId;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SoilPlanning extends AppCompatActivity {

    private Spinner soilTypeSpinner, phLevelSpinner;
    private String selectedSoil, selectedPhLevel;
    private EditText soilTexture, wettingCycle;
    private Button submitReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_planning);

        // Initialize views
        soilTexture = findViewById(R.id.soilTexture);
        wettingCycle = findViewById(R.id.wettingCycle);
        submitReport = findViewById(R.id.submitSoilReport);

        // Set up spinners
        setUpSpinners();

        // Set click listener for submit button
        submitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushInputs();
            }
        });
    }

    private void setUpSpinners() {
        // Initialize spinners
        soilTypeSpinner = findViewById(R.id.soilSpinner);
        phLevelSpinner = findViewById(R.id.ph_levels);

        // Set up ArrayAdapter and OnItemSelectedListener for soilTypeSpinner
        ArrayAdapter<CharSequence> soilAdapter = ArrayAdapter.createFromResource(
                this, R.array.soil_textures, android.R.layout.simple_spinner_item
        );
        soilAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        soilTypeSpinner.setAdapter(soilAdapter);
        soilTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) {
                selectedSoil = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Set up ArrayAdapter and OnItemSelectedListener for phLevelSpinner
        ArrayAdapter<CharSequence> phLevelAdapter = ArrayAdapter.createFromResource(
                this, R.array.ph_level_options, android.R.layout.simple_spinner_item
        );
        phLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        phLevelSpinner.setAdapter(phLevelAdapter);
        phLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) {
                selectedPhLevel = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void pushInputs() {
        // Create a map to store the input values
        Map<String, Object> reportMap = new HashMap<>();
        reportMap.put("SoilType", selectedSoil);
        reportMap.put("Texture", soilTexture.getText().toString());
        reportMap.put("ph Level", selectedPhLevel);
        reportMap.put("Wetting Cycle", wettingCycle.getText().toString());

        // Generate a report ID
        String reportId = generateReportId();

        // Push the input values to the Firebase Realtime Database
        FirebaseDatabase.getInstance().getReference().child("SoilPlanner").child(reportId).setValue(reportMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent intent = new Intent(SoilPlanning.this, MainActivity.class); // Use CropPlanting.this as the first argument
                        startActivity(intent);
                        finish();
                    }
                });
    }
}
