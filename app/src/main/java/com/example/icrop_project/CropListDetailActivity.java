package com.example.icrop_project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CropListDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_list_detail);

        CropData model = (CropData) getIntent().getSerializableExtra("model");

        // Map view elements to their respective IDs
        TextView[] textViews = {
                findViewById(R.id.cropNameDetail),
                findViewById(R.id.userIDDetail),
                findViewById(R.id.reportIDDetail),
                findViewById(R.id.dateHarvestDetail),
                findViewById(R.id.dateTimeReportedDetail),
                findViewById(R.id.datePlantedDetail)
        };

        // Populate textViews with corresponding data
        String[] data = {
                model.getCropType(),
                model.getUserID(),
                model.getReportID(),
                model.getDateHarvest(),
                model.getDateReportSubmitted(),
                model.getDatePlanted()
        };

        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setText(data[i]);
        }
    }
}
