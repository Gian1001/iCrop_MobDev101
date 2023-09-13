package com.example.icrop_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private FirebaseAuth auth;
    private Button button;
    private TextView textView;
    private FirebaseUser user;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private CropListAdapter cropAdapter;
    private ArrayList<CropData> cropList;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        recyclerView = view.findViewById(R.id.reportList);
        databaseReference = FirebaseDatabase.getInstance().getReference("CropPlanner");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        cropList = new ArrayList<>();
        cropAdapter = new CropListAdapter(requireContext(), cropList);
        recyclerView.setAdapter(cropAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    CropData cropChildrenVal = dataSnapshot.getValue(CropData.class);
                    cropList.add(cropChildrenVal);
                }
                cropAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        initView(view);
        checkUserLogin();
        setButtonClickListeners();
        return view;

    }

    private void initView(View view) {
        button = view.findViewById(R.id.logout);
        textView = view.findViewById(R.id.user_details);
    }

    private void checkUserLogin() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null) {
            redirectToLogin();
        } else {
            textView.setText(user.getEmail());
        }
    }

    private void setButtonClickListeners() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOutUser();
                redirectToLogin();
            }
        });
    }

    private void redirectToLogin() {
        Intent intent = new Intent(getActivity(), Login.class);
        startActivity(intent);
        removeFragmentFromBackStack();
    }

    private void signOutUser() {
        FirebaseAuth.getInstance().signOut();
    }

    private void removeFragmentFromBackStack() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().remove(this).commit();
    }
}
