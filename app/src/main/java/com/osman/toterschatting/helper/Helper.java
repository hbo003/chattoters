package com.osman.toterschatting.helper;

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.osman.toterschatting.R;

public class Helper {

    public static void toast(String s, AppCompatActivity activity) {
        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
    }

    public static void transFragment(Fragment fragment, Parcelable value, AppCompatActivity appCompatActivity) {
        if (value != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("user", value);
            fragment.setArguments(bundle);
        }
        FragmentTransaction ft = appCompatActivity.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, fragment, fragment.getTag());
        ft.commit();
    }
}
