package com.example.icrop_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CommunityReportsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_reports_detail);


        CropData model = (CropData) getIntent().getSerializableExtra("model");
        Button goBackButton = findViewById(R.id.returnButton);


        TextView[] textViews = {
                findViewById(R.id.cropNameCommunity),
                findViewById(R.id.userIDCommunity),
                findViewById(R.id.reportIDCommunity),
                findViewById(R.id.dateTimeReportedCommunity),
                findViewById(R.id.datePlantedCommunity),
                findViewById(R.id.dateHarvestCommunity)
        };


        String[] data = {
                model.getCropType(),
                model.getUserID(),
                model.getReportID(),
                model.getDateTimeReported(),
                model.getDatePlanted(),
                model.getDateHarvest()
        };
        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setText(data[i]);
        }


        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }
}