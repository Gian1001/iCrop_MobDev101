package com.example.icrop_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CropPlanting extends AppCompatActivity {

    private DatePickerDialog plantingDatePickerDialog, harvestDatePickerDialog;
    private Button dateButton, submitButton, btnChooseImg;
    private TextInputEditText harvestButton, inputCropType, plantingButton;
    private Spinner spinnerCrops;
    private String selectedCropType;
    private String selectedImageUrl;
    private Uri selectedImageUri;


    private int selectedButtonId;
    private ImageView imageViewChooseImg;
    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_planting);
        FirebaseApp.initializeApp(this);

        initializeViews();

        initDatePicker(R.id.plantingButton);
        initDatePicker(R.id.harvestDate);
    }

    private void initializeViews() {
        plantingButton = findViewById(R.id.plantingButton);
        plantingButton.setText(getTodaysDate());

        harvestButton = findViewById(R.id.harvestDate);
        harvestButton.setText(getTodaysDate());


        submitButton = findViewById(R.id.submitPlantReport);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedCrop = spinnerCrops.getSelectedItem().toString();
                String getPlantedDate = plantingButton.getText().toString();
                String getSoilDate = harvestButton.getText().toString();

                if (selectedCrop.equals("Select Crop Type")) {
                    Toast.makeText(getApplicationContext(), "Please select a crop type", Toast.LENGTH_SHORT).show();
                } else if (getSoilDate.equals(getTodaysDate())) {
                    Toast.makeText(getApplicationContext(), "Please input correct soil rotation date", Toast.LENGTH_SHORT).show();
                } else {
                    pushInputs();
                }
            }
        });

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
                inputCropType.setText(selectedCropType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        inputCropType = findViewById(R.id.inputCropType);
        inputCropType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerCrops.performClick();
            }
        });

        imageViewChooseImg = findViewById(R.id.imageViewChooseImg);
        btnChooseImg = findViewById(R.id.btnChooseImg);

        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
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
        String getUserID = accessUserID();
        String reportId = generateReportId();
        String getTimeReported = getCurrentDateTime();

        if (selectedImageUri != null) {
            uploadImageToStorage(reportId, getUserID, getTimeReported);
        } else {
            pushDataWithoutImage(reportId, getUserID, getTimeReported);
        }
    }

    private void uploadImageToStorage(final String reportId, final String userID, final String timeReported) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" + reportId);

        storageRef.putFile(selectedImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Image URL
                                selectedImageUrl = uri.toString();

                                pushDataWithImage(reportId, userID, timeReported);
                            }
                        });
                    }
                });
    }

    private void pushDataWithImage(final String reportId, String userID, String timeReported) {
        // Create a map to store the input values including the image URL
        Map<String, Object> reportMap = new HashMap<>();
        reportMap.put("CropType", selectedCropType);
        reportMap.put("datePlanted", plantingButton.getText().toString());
        reportMap.put("dateHarvest", harvestButton.getText().toString());
        reportMap.put("userID", userID);
        reportMap.put("reportID", reportId);
        reportMap.put("DateTimeReported", timeReported);
        reportMap.put("ImageUrl", selectedImageUrl); // Store the image URL

        FirebaseDatabase.getInstance().getReference().child("CropPlanner").child(reportId).setValue(reportMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CropPlanting.this, "Report is Submitted. View Profile to see report.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CropPlanting.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    private void pushDataWithoutImage(final String reportId, String userID, String timeReported) {
        Map<String, Object> reportMap = new HashMap<>();
        reportMap.put("CropType", selectedCropType);
        reportMap.put("datePlanted", plantingButton.getText().toString());
        reportMap.put("dateHarvest", harvestButton.getText().toString());
        reportMap.put("userID", userID);
        reportMap.put("reportID", reportId);
        reportMap.put("DateTimeReported", timeReported);

        FirebaseDatabase.getInstance().getReference().child("CropPlanner").child(reportId).setValue(reportMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CropPlanting.this, "Report is Submitted. View Profile to see report.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CropPlanting.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }


    private void initDatePicker(final int buttonId) {
        final View dateView = findViewById(buttonId);
        final DatePickerDialog datePickerDialog;

        datePickerDialog = createDatePickerDialog(buttonId);

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
                selectedButtonId = buttonId;
            }
        });
    }

    private DatePickerDialog createDatePickerDialog(int buttonId) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);

                TextInputEditText selectedButton = findViewById(selectedButtonId);
                selectedButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        if (buttonId == R.id.plantingButton) {
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        }

        return datePickerDialog;
    }



    private String makeDateString(int day, int month, int year) {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        if (month >= 1 && month <= 12) {
            return months[month - 1] + " " + day + " " + year;
        }
        return "Jan" + " " + day + " " + year;
    }

    public static String generateReportId() {

        //fix format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());

        Random random = new Random();
        int randomNumber = random.nextInt(10000); // You can adjust the range as needed

        return timestamp + String.format("%04d", randomNumber);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            imageViewChooseImg.setImageURI(imageUri); // Set the image in ImageView

            // Save the selected image URI for later use in uploadImageToStorage
            selectedImageUri = imageUri;
        }
    }

    public String getCurrentDateTime() {
        // Get the current date and time
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Note: Month is zero-based, so add 1
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Format the date and time as "YYYY-MM-DD HH:MM"
        String formattedDateTime = String.format("%04d-%02d-%02d %02d:%02d", year, month, day, hour, minute);

        return formattedDateTime;
    }

    private String accessUserID(){
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return preferences.getString("userID", "");

    }
}
