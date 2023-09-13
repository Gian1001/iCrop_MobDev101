package com.example.icrop_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ListFragment extends Fragment {

    private RecyclerView recyclerView, recyclerView2, recyclerView3;
    private DatabaseReference databaseReference, databaseReference2, databaseReference3;
    private CropListInfoAdapter cropInfoAdapter;
    private PestAdapter pestAdapter;
    private ArrayList<PestData> pestArrayList;
    private ArrayList<CropInformationData> cropInfoList;

    private SoilListAdapter soilListAdapter;

    private ArrayList<SoilData> soilArrayList;


    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = rootView.findViewById(R.id.cropInformationList);
        databaseReference = FirebaseDatabase.getInstance().getReference("crops_db").child("crops");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        cropInfoList = new ArrayList<>();
        cropInfoAdapter = new CropListInfoAdapter(requireContext(), cropInfoList);
        recyclerView.setAdapter(cropInfoAdapter);

        recyclerView2 = rootView.findViewById(R.id.pestInformationList);
        databaseReference2 = FirebaseDatabase.getInstance().getReference("pests_db").child("pests_and_diseases");
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(requireContext()));

        pestArrayList = new ArrayList<>();
        pestAdapter = new PestAdapter(requireContext(), pestArrayList);
        recyclerView2.setAdapter(pestAdapter);

        recyclerView3 = rootView.findViewById(R.id.soilInformationList);
        databaseReference3 = FirebaseDatabase.getInstance().getReference("pests").child("pests_and_diseases");
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(requireContext()));

        soilArrayList = new ArrayList<>();
        soilListAdapter = new SoilListAdapter(requireContext(), soilArrayList);
        recyclerView3.setAdapter(soilListAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CropInformationData cropInfoChildren = dataSnapshot.getValue(CropInformationData.class);
                    cropInfoList.add(cropInfoChildren);
                }
                cropInfoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PestData pestData = dataSnapshot.getValue(PestData.class);
                    pestArrayList.add(pestData);
                }
                pestAdapter.notifyDataSetChanged();
            }

            //to be fixed/optimised later
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        databaseReference3.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    SoilData soilData = dataSnapshot.getValue(SoilData.class);
//                    soilArrayList.add(soilData);
//                }
//                soilListAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        return rootView;
    }


}