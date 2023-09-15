package com.example.icrop_project;

import static com.example.icrop_project.CropPlanting.generateReportId;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SoilPlanning extends AppCompatActivity {

    private Spinner soilTypeSpinner, phLevelSpinner;
    private String selectedSoil, selectedPhLevel;
    private TextInputEditText soilTexture, getWettingCycle, inPhLevel, inputSoilType;
    private Button submitReport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_planning);

        // Initialize views
        soilTexture = findViewById(R.id.soilTexture);
        getWettingCycle = findViewById(R.id.wettingCycle);
        submitReport = findViewById(R.id.submitSoilReport);


        setUpSpinners();

        submitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedSoilType = soilTypeSpinner.getSelectedItem().toString();
                String soilProportionInputs = soilTexture.getText().toString();
                String phLevel = phLevelSpinner.getSelectedItem().toString();
                String wettingCycle = getWettingCycle.getText().toString();
                String[] soilTextures = getResources().getStringArray(R.array.soil_textures);
                String[] phLevels = getResources().getStringArray(R.array.ph_level_options);


                if(selectedSoilType.equals(soilTextures[0])){
                    Toast.makeText(getApplicationContext(), "Please select a soil type", Toast.LENGTH_SHORT).show();
                }else if(soilProportionInputs.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please input a soil texture. See List Tab to view soil types and its textures.", Toast.LENGTH_SHORT).show();
                }else if(phLevel.equals(phLevels[0])){
                    Toast.makeText(getApplicationContext(), "Please select a ph Level. See List Tab to view soil types and its textures.", Toast.LENGTH_SHORT).show();
                }else if(wettingCycle.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please include your wetting cycle.", Toast.LENGTH_SHORT).show();
                }else{
                    pushInputs();
                }
            }
        });
    }

    private void setUpSpinners() {
        // Initialize spinners
        soilTypeSpinner = findViewById(R.id.soilSpinner);
        phLevelSpinner = findViewById(R.id.ph_levels);

        // spinner triggers (click texteditinput)
        inputSoilType = findViewById(R.id.inputSoilType);
        inputSoilType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soilTypeSpinner.performClick();
            }
        });

        inPhLevel = findViewById(R.id.inPhLevel);
        inPhLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phLevelSpinner.performClick();
            }
        });

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
                inputSoilType.setText(selectedSoil);
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
                inPhLevel.setText(selectedPhLevel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void pushInputs() {
        CropPlanting getTimeMethod = new CropPlanting();

        String accessUserID = accessUserID();
        String reportId = generateReportId();

        // Create a map to store the input values
        Map<String, Object> reportMap = new HashMap<>();
        reportMap.put("SoilType", selectedSoil);
        reportMap.put("Texture", soilTexture.getText().toString());
        reportMap.put("ph Level", selectedPhLevel);
        reportMap.put("userID", accessUserID);
        reportMap.put("reportID", reportId);
        reportMap.put("Wetting Cycle", getWettingCycle.getText().toString());



        FirebaseDatabase.getInstance().getReference().child("SoilPlanner").child(reportId).setValue(reportMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(SoilPlanning.this, "Report is Submitted. View Profile to see report.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SoilPlanning.this, MainActivity.class); // Use CropPlanting.this as the first argument
                        startActivity(intent);
                        finish();
                    }
                });
    }

    private String accessUserID(){
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return preferences.getString("userID", ""); // "" is the default value if userID is not found

    }
}
