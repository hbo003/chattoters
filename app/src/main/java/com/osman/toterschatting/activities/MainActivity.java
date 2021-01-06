package com.osman.toterschatting.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.osman.toterschatting.helper.BaseActivity;
import com.osman.toterschatting.R;
import com.osman.toterschatting.helper.Helper;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    MaterialButton mRegisterButton, mLoginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
    }

    private void setupView() {
        mLoginButton = findViewById(R.id.login_user);
        mRegisterButton = findViewById(R.id.register_user);

        mRegisterButton.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login_user){
            Helper.toast("Login Page... ", this);
            startActivity(new Intent(this, LoginActivity.class));
        }
        else if (view.getId() == R.id.register_user){
            Helper.toast("Register Page... ", this);
            startActivity(new Intent(this, RegisterActivity.class));

      }
    }


}