package com.osman.toterschatting.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.osman.toterschatting.R;
import com.osman.toterschatting.helper.BaseActivity;
import com.osman.toterschatting.helper.Helper;

import java.util.HashMap;

public class RegisterActivity extends BaseActivity {

    EditText email, password;
    MaterialButton registerBtn;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupView();
        setupCheck();
    }

    private void setupCheck() {
        registerBtn.setOnClickListener(view -> {
            if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                email.setError("Invalid Email");
                email.setFocusable(true);

            }
            else if (password.getText().toString().length() < 6) {
                password.setError("Password must at least 6 character");
                password.setFocusable(true);

            }
            else {

                registerUser(email.getText().toString(), password.getText().toString());
            }

        });
    }

    private void registerUser(String email, String password) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        progressDialog.dismiss();
                        FirebaseUser user = mAuth.getCurrentUser();

                        String uid = user.getUid();
                        String emailUser = user.getEmail();
                        HashMap<Object, String> hashMap = new HashMap<>();
                        hashMap.put("email", emailUser);
                        hashMap.put("uid", uid);
                        hashMap.put("name", "");
                        hashMap.put("phone", "");
                        hashMap.put("image", "");
                        DatabaseReference myRef = database.getReference("Users");
                        myRef.child(uid).setValue(hashMap);

                        Helper.toast("Register success to " +user.getEmail(), RegisterActivity.this);
                        startActivity(new Intent(RegisterActivity.this, DashboardActivity.class));
                        finish();


                    } else {
                        progressDialog.dismiss();
                        // If sign in fails, display a message to the user.
                        Helper.toast("Authentication failed.", RegisterActivity.this);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Helper.toast(e.getMessage(), RegisterActivity.this);
            }
        });
    }

    private void setupView() {
        email = findViewById(R.id.email_register);
        password = findViewById(R.id.password_register);
        registerBtn = findViewById(R.id.register_btn);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Register User...");
    }
}