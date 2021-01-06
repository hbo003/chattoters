package com.osman.toterschatting.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ChatsUser implements Parcelable {
    String message, sender, receiver, timestamp;
   // boolean isSeen;
public ChatsUser(){}
    public ChatsUser(String message, String sender, String receiver, String timestamp) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
        //this.isSeen = isSeen;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

//    public boolean isSeen() {
//        return isSeen;
//    }
//
//    public void setSeen(boolean seen) {
//        isSeen = seen;
//    }
}
