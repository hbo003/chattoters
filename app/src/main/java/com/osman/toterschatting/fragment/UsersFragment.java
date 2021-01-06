package com.osman.toterschatting.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.osman.toterschatting.R;
import com.osman.toterschatting.activities.DashboardActivity;
import com.osman.toterschatting.helper.BaseFragment;
import com.osman.toterschatting.model.ModelUser;
import com.osman.toterschatting.model.UserAdapter;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends BaseFragment {

    RecyclerView recyclerView;
    UserAdapter userAdapter;
    List<ModelUser> modelUserList = new ArrayList<>();

    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.users_recycleView);
        userAdapter = new UserAdapter((AppCompatActivity) getActivity(), modelUserList);
        recyclerView.setAdapter(userAdapter);
        getAllUsers();

    }

    private void getAllUsers() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modelUserList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ModelUser modelUser = ds.getValue(ModelUser.class);
                    if (modelUser.getUid() != null && firebaseUser.getUid() != null){
                    if (!modelUser.getUid().equals(firebaseUser.getUid())) {
                        modelUserList.add(modelUser);
                        userAdapter.notifyDataSetChanged();
                    }
                }}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}