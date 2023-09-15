package com.example.icrop_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;
import com.squareup.picasso.Picasso;

public class CropListDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_list_detail);

        CropData model = (CropData) getIntent().getSerializableExtra("model");
        Button goBackButton = findViewById(R.id.goBackButton);
        TextInputEditText dateHarvest = findViewById(R.id.dateHarvestDetail);
        ImageView imageView = findViewById(R.id.loadImage); // Add ImageView
        Button updateButton = findViewById(R.id.updateHarvestDate);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();





        // Map view elements to their respective IDs
        TextView[] textViews = {
                findViewById(R.id.cropNameDetail),
                findViewById(R.id.userIDDetail),
                findViewById(R.id.reportIDDetail),
                findViewById(R.id.dateTimeReportedDetail),
                findViewById(R.id.datePlantedDetail)
        };


        // Populate textViews with corresponding data
        String[] data = {
                model.getCropType(),
                model.getUserID(),
                model.getReportID(),
                model.getDateTimeReported(),
                model.getDatePlanted()
        };

        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setText(data[i]);
        }
        dateHarvest.setText(model.getDateHarvest());
        String imageUrl = model.getImageUrl(); // Get the image URL from CropData
        Picasso.get().load(imageUrl).into(imageView); // Load image into ImageView

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the new harvest date from the TextInputEditText
                TextInputEditText dateHarvestDetailEditText = findViewById(R.id.dateHarvestDetail);
                String newHarvestDate = dateHarvestDetailEditText.getText().toString().trim();

                // Update the Firebase database with the new harvest date
                DatabaseReference harvestDateRef = databaseReference.child("CropPlanner").child(model.getReportID()).child("dateHarvest");
                harvestDateRef.setValue(newHarvestDate)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Harvest Date is Updated", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CropListDetailActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "App cannot be updated at the moment. Try restarting.", Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });

    }
}
