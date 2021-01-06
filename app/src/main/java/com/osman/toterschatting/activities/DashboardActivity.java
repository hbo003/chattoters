package com.osman.toterschatting.activities;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.osman.toterschatting.R;
import com.osman.toterschatting.fragment.HomeFragment;
import com.osman.toterschatting.fragment.ProfileFragment;
import com.osman.toterschatting.fragment.UsersFragment;
import com.osman.toterschatting.helper.BaseActivity;
import com.osman.toterschatting.helper.Helper;

public class DashboardActivity extends BaseActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_dashboard);

        bottomNavigationView = findViewById(R.id.nav_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(itemSelectedListener);

        Helper.transFragment(new UsersFragment(), null, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkStatus(DashboardActivity.this);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = item -> {

        switch (item.getItemId()) {
            case R.id.nav_home:
               // Helper.transFragment(new HomeFragment(), null, DashboardActivity.this);
                return false;
            case R.id.nav_profile:
                Helper.transFragment(new ProfileFragment(), null, DashboardActivity.this);
                return true;
            case R.id.nav_users:
                Helper.transFragment(new UsersFragment(), null, DashboardActivity.this);
                return true;

        }

        return false;
    };


}