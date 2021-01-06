package com.osman.toterschatting.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelUser implements Parcelable {
    String email, image, name, phone, uid;

    ModelUser() {
    }

    public ModelUser(String email, String image, String name, String phone, String uid) {
        this.email = email;
        this.image = image;
        this.name = name;
        this.phone = phone;
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
