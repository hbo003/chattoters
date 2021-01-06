package com.osman.toterschatting.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.osman.toterschatting.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.MyHolder>{

    private static final int MSGLEFT = 0;
    private static final int MSGRIGHT = 1;
    Context context;
    List<ChatsUser> chatsUserList;
    String imageUrl;
   FirebaseUser firebaseUser;
    public AdapterChat(Context context, List<ChatsUser> chatsUserList, String imageUrl) {
        this.context = context;
        this.chatsUserList = chatsUserList;
        this.imageUrl = imageUrl;
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (chatsUserList.get(position).getSender().equals(firebaseUser.getUid())){
            return MSGRIGHT;
        }else {

            return MSGLEFT;
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType== MSGLEFT){
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat_left, parent, false);
            return new MyHolder(view);
        } else  {
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat_right, parent, false);
            return new MyHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        ChatsUser chatsUser = chatsUserList.get(position);

        Calendar cal =  Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(chatsUser.timestamp));
        String dateTime = android.text.format.DateFormat.format("hh:mm aa", cal).toString();
        holder.timeTV.setText(dateTime);
        holder.messageTV.setText(chatsUser.getMessage());
        try{
            Picasso.get().load(imageUrl).into(holder.imageView);
        }catch (Exception e){}
    }

    @Override
    public int getItemCount() {
        return chatsUserList.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView messageTV, timeTV;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_hisUser);
            messageTV = itemView.findViewById(R.id.chats_hisUser);
            timeTV = itemView.findViewById(R.id.date_chat_hisUser);
        }
    }
}
