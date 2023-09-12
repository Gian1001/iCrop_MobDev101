package com.example.icrop_project;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.icrop_project.CropPlanting;
import com.example.icrop_project.R;

public class ManageFragment extends Fragment {

    private ImageButton manageSoilButton, manageCropButton; // Declare the Button

    public ManageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage, container, false);

        manageCropButton = view.findViewById(R.id.manageCropButton);

        manageCropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Define the action when the Button is clicked (e.g., navigate to another activity)
                // For example, starting a new activity:
                Intent intent = new Intent(getActivity(), CropPlanting.class);
                startActivity(intent);
            }
        });
        manageSoilButton = view.findViewById(R.id.manageSoilButton);

        manageSoilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Define the action when the ImageButton is clicked (e.g., navigate to another activity)
                // For example, starting a new activity:
                Intent intent = new Intent(getActivity(), SoilPlanning.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
