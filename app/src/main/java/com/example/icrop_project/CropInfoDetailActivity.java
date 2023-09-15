package com.example.icrop_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CropInfoDetailActivity extends AppCompatActivity {

    Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_info_detail);

        returnButton = findViewById(R.id.returnButton);


        CropInformationData model = (CropInformationData) getIntent().getSerializableExtra("model");
        TextView[] textViews = {
                findViewById(R.id.cropNameInfoDetail),
                findViewById(R.id.soilInfoDetail),
                findViewById(R.id.datePlantedInfoDetail),
                findViewById(R.id.descriptionCropDetail),
                findViewById(R.id.dateHarvestInfoDetail),
                findViewById(R.id.temperatureCropDetail),
                findViewById(R.id.sunlightCropDetail),
                findViewById(R.id.cropTypeInfoDetail),
                findViewById(R.id.waterCycleInfoDetail)
        };

        // Populate textViews with corresponding data
        String[] data = {
                model.getName(),
                model.getSoil_type(),
                model.getStart_date(),
                model.getDescription(),
                model.getEnd_date(),
                model.getTemperature(),
                model.getSunlight(),
                model.getType(),
                model.getWatering()
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