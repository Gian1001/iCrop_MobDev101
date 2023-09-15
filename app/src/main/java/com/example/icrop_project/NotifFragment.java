package com.example.icrop_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class NotifFragment extends Fragment {


    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private CropListAdapter cropAdapter;
    private ArrayList<CropData> cropList;
    public NotifFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notif, container, false);
        recyclerView = view.findViewById(R.id.communityReports);

        databaseReference = FirebaseDatabase.getInstance().getReference("CropPlanner");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        recyclerView.setItemAnimator(null);

        cropList = new ArrayList<>();
        cropAdapter = new CropListAdapter(requireContext(), cropList);
        recyclerView.setAdapter(cropAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cropList.clear(); // Clear the list before adding sorted items
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CropData cropChildrenVal = dataSnapshot.getValue(CropData.class);
                    cropList.add(cropChildrenVal);
                }
                Collections.sort(cropList, new Comparator<CropData>() {
                    @Override
                    public int compare(CropData crop1, CropData crop2) {
                        // Compare reportIDs as strings in descending order
                        return crop2.getReportID().compareTo(crop1.getReportID());
                    }
                });

                cropAdapter.notifyDataSetChanged();

                // Sort the list by "reportID" (or any other field you want to sort by)
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors here
            }
        });

        return view;
    }
}