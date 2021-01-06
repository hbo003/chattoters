package com.osman.toterschatting.helper;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.osman.toterschatting.R;
import com.osman.toterschatting.activities.DashboardActivity;
import com.osman.toterschatting.activities.MainActivity;
import com.osman.toterschatting.activities.ProfileActivity;

public class BaseActivity extends AppCompatActivity {
    public FirebaseAuth mAuth;
    public FirebaseUser firebaseUser;
    public FirebaseDatabase database;
    public DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        databaseReference = database.getReference("Users");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (this.getClass().getName().equals(DashboardActivity.class.getName())
                || this.getClass().getName().equals(ProfileActivity.class.getName())
        ) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout_menu) {
            mAuth.signOut();
            firebaseUser = null;
            mAuth = null;
            checkStatus(this);
        }

        return super.onOptionsItemSelected(item);

    }

    public void checkStatus(AppCompatActivity appCompatActivity) {
        if (firebaseUser == null) {
            startActivity(new Intent(appCompatActivity, MainActivity.class));
            finish();
        }
    }
}
