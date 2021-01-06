package com.osman.toterschatting.model;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.osman.toterschatting.R;
import com.osman.toterschatting.fragment.HomeFragment;
import com.osman.toterschatting.helper.Helper;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {


    AppCompatActivity context;
    List<ModelUser> modelUserList;

    public UserAdapter(AppCompatActivity context, List<ModelUser> modelUserList) {
        this.context = context;
        this.modelUserList = modelUserList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;
        public final ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.photo_users);
            textView = view.findViewById(R.id.name_users);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        ModelUser modelUser = modelUserList.get(position);
        String name = modelUser.getEmail().substring(0, modelUser.getEmail().lastIndexOf("@"));
        viewHolder.textView.setText(name);
        try {
            Picasso.get().load(modelUser.image).placeholder(R.drawable.logototers).into(viewHolder.imageView);
        } catch (Exception e) {
            Picasso.get().load(R.drawable.logototers).placeholder(R.drawable.logototers).into(viewHolder.imageView);
        }
        viewHolder.itemView.setOnClickListener((View.OnClickListener) view -> Helper.transFragment(new HomeFragment(), (Parcelable) modelUser, context));
    }

    @Override
    public int getItemCount() {
        return modelUserList.size();
    }


}
