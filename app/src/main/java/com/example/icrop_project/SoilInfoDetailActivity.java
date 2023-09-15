package com.example.icrop_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SoilInfoDetailActivity extends AppCompatActivity {


    Button returnButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_info_detail);


        returnButton = findViewById(R.id.ReturnButton);

        SoilData model = (SoilData) getIntent().getSerializableExtra("model");
        TextView[] textViews = {
                findViewById(R.id.soilNameDetail),
                findViewById(R.id.soilContentDetail),
                findViewById(R.id.soilIssuesDetail),
                findViewById(R.id.soilOrganicDetail),
                findViewById(R.id.soilPHRangeDetail),
                findViewById(R.id.soilBestCropsDetail),
                findViewById(R.id.soilDescriptionDetail)

        };

        // Populate textViews with corresponding data
        String[] data = {
                model.getName(),
                model.getNutrient_content(),
                model.getCommon_issues(),
                model.getOrganic_matter_content(),
                model.getPh_range(),
                model.getBest_crops(),
                model.getDescription()
                
        };

        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setText(data[i]);
        }

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}