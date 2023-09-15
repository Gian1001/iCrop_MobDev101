package com.example.icrop_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class PestInfoDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pest_info_detail);

        PestData model = (PestData) getIntent().getSerializableExtra("model");
        TextView[] textViews = {
                findViewById(R.id.pestNameDetail),
                findViewById(R.id.pestControlDetail),
                findViewById(R.id.pestDescriptionDetail),
                findViewById(R.id.pestSymptomsDetail),
                findViewById(R.id.pestPreventionDetail),
                findViewById(R.id.pestPlantsDetail),

        };

        // Populate textViews with corresponding data
        String[] data = {
                model.getName(),
                model.getControl_methods(),
                model.getDescription(),
                model.getDamage_symptoms(),
                model.getPrevention_methods(),
                model.getAffected_plants()
        };

        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setText(data[i]);
        }
    }
}