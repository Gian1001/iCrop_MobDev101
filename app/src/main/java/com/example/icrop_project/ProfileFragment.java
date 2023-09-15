package com.example.icrop_project;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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



        String userID = accessUserID();
        databaseReference = FirebaseDatabase.getInstance().getReference("CropPlanner");
        Query userQuery = databaseReference.orderByChild("userID").equalTo(userID);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        recyclerView.setItemAnimator(null);

        cropList = new ArrayList<>();
        cropAdapter = new CropListAdapter(requireContext(), cropList);
        recyclerView.setAdapter(cropAdapter);

        userQuery.addValueEventListener(new ValueEventListener() {
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

        initView(view);
        checkUserLogin();
        setButtonClickListeners();
        return view;

    }

    private void initView(View view) {
        button = view.findViewById(R.id.logout);
        //textView = view.findViewById(R.id.user_details);
    }

    private void checkUserLogin() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null) {
            redirectToLogin();
        } else {
          //  textView.setText(user.getEmail());
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


    //not sure pa if i clear nalang or hayaan yung userid sa preferences
    private void clearUserID() {
        SharedPreferences preferences = getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("userID"); // Remove the user ID preference
        editor.apply(); // Apply changes
    }

    //di gumagana pag nakainstantiate yung accessuserid na method kaya nakaganto lang
    private String accessUserID(){
        SharedPreferences preferences = getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return preferences.getString("userID", ""); // "" is the default value if userID is not found

    }
}
