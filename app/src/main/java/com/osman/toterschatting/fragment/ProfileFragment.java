package com.osman.toterschatting.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.osman.toterschatting.R;
import com.osman.toterschatting.activities.MainActivity;
import com.osman.toterschatting.helper.BaseFragment;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class ProfileFragment extends BaseFragment {

    private static final int RESULT_LOAD_IMG = 123;
    ImageView imageView;
    TextView textView, textViewLogout;
    FloatingActionButton floatingActionButton;
    private StorageReference storageRef;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storageRef = FirebaseStorage.getInstance().getReference();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupView(view);
        floatingActionButton.setOnClickListener(view1 -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/.jpg");
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
        });
        Query query = databaseReference.orderByChild("email").equalTo(firebaseUser.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String email = "" + dataSnapshot.child("email").getValue();
                    String image = "" + dataSnapshot.child("image").getValue();
                    textView.setText(email);
                    try {
                        Picasso.get().load(image).placeholder(R.drawable.logototers).into(imageView);
                    } catch (Exception e) {
                        Picasso.get().load(R.drawable.logototers).placeholder(R.drawable.logototers).into(imageView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void setupView(View view) {
        imageView = view.findViewById(R.id.image_profile);
        textView = view.findViewById(R.id.email_profile);
        floatingActionButton = view.findViewById(R.id.add_profile);
        textViewLogout = view.findViewById(R.id.logout_profile);
        textViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                firebaseUser = null;
                mAuth = null;
                checkStatus(getActivity());
            }
        });
    }

    public void checkStatus(Context appCompatActivity) {
        if (firebaseUser == null) {

            startActivity(new Intent(appCompatActivity, MainActivity.class));
            this.getActivity().finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == -1) {
            try {
                Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);

                StorageReference riversRef = storageRef.child("images" + firebaseUser.getUid());

                riversRef.putFile(imageUri)
                        .addOnSuccessListener(taskSnapshot -> {
                            // Get a URL to the uploaded content
                            Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                            while (!task.isSuccessful()) ;
                            Uri uri = task.getResult();
                            if (task.isSuccessful()) {
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("image", uri.toString());
                                databaseReference.child(firebaseUser.getUid()).updateChildren(hashMap);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                // ...
                            }
                        });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }
}