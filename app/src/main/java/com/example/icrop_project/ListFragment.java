package com.example.icrop_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ListFragment extends Fragment {

    private RecyclerView recyclerView1, recyclerView2, recyclerView3;
    private DatabaseReference databaseReference1, databaseReference2, databaseReference3;
    private CropListInfoAdapter cropInfoAdapter;
    private PestAdapter pestAdapter;
    private ArrayList<PestData> pestArrayList;
    private ArrayList<CropInformationData> cropInfoList;
    private SoilListAdapter soilListAdapter;
    private ArrayList<SoilData> soilArrayList;
    private Button returnButton;

    private static final String CROPS_DB_PATH = "crops_db/crops";
    private static final String PESTS_DB_PATH = "pests_db/pests_and_diseases";
    private static final String SOILS_DB_PATH = "soils_db/soils";

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView1 = rootView.findViewById(R.id.cropInformationList);
        recyclerView2 = rootView.findViewById(R.id.pestInformationList);
        recyclerView3 = rootView.findViewById(R.id.soilInformationList);

        setupRecyclerView(recyclerView1, CROPS_DB_PATH);
        setupRecyclerView(recyclerView2, PESTS_DB_PATH);
        setupRecyclerView(recyclerView3, SOILS_DB_PATH);



        return rootView;
    }

    private void setupRecyclerView(RecyclerView recyclerView, String dbPath) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        if (dbPath.equals(CROPS_DB_PATH)) {
            cropInfoList = new ArrayList<>();
            cropInfoAdapter = new CropListInfoAdapter(requireContext(), cropInfoList);
            recyclerView.setAdapter(cropInfoAdapter);
        } else if (dbPath.equals(PESTS_DB_PATH)) {
            pestArrayList = new ArrayList<>();
            pestAdapter = new PestAdapter(requireContext(), pestArrayList);
            recyclerView.setAdapter(pestAdapter);
        } else if (dbPath.equals(SOILS_DB_PATH)) {
            soilArrayList = new ArrayList<>();
            soilListAdapter = new SoilListAdapter(requireContext(), soilArrayList);
            recyclerView.setAdapter(soilListAdapter);
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(dbPath);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clear the data list before adding new data
                if (dbPath.equals(CROPS_DB_PATH)) {
                    cropInfoList.clear();
                } else if (dbPath.equals(PESTS_DB_PATH)) {
                    pestArrayList.clear();
                } else if (dbPath.equals(SOILS_DB_PATH)) {
                    soilArrayList.clear();
                }

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dbPath.equals(CROPS_DB_PATH)) {
                        CropInformationData cropInfoChildren = dataSnapshot.getValue(CropInformationData.class);
                        cropInfoList.add(cropInfoChildren);
                    } else if (dbPath.equals(PESTS_DB_PATH)) {
                        PestData pestData = dataSnapshot.getValue(PestData.class);
                        pestArrayList.add(pestData);
                    } else if (dbPath.equals(SOILS_DB_PATH)) {
                        SoilData soilData = dataSnapshot.getValue(SoilData.class);
                        soilArrayList.add(soilData);
                    }
                }

                // Notify the adapter of data changes
                if (dbPath.equals(CROPS_DB_PATH)) {
                    cropInfoAdapter.notifyDataSetChanged();
                } else if (dbPath.equals(PESTS_DB_PATH)) {
                    pestAdapter.notifyDataSetChanged();
                } else if (dbPath.equals(SOILS_DB_PATH)) {
                    soilListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
