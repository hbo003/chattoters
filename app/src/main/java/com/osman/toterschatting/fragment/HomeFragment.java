package com.osman.toterschatting.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.osman.toterschatting.R;
import com.osman.toterschatting.helper.BaseFragment;
import com.osman.toterschatting.model.AdapterChat;
import com.osman.toterschatting.model.ChatsUser;
import com.osman.toterschatting.model.ModelUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomeFragment extends BaseFragment {


    Toolbar toolbar;
    RecyclerView recyclerView;
    TextView textViewName;
    EditText editText;
    ImageView imageViewUser;
    ImageButton imageSend;
    String userUid = "", myUid, name = "", urlImage = "";
    ValueEventListener eventListener;
    List<ChatsUser> chatsUserList = new ArrayList<>();
    AdapterChat adapterChat;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Parcelable user = bundle.getParcelable("user");
            userUid = ((ModelUser) user).getUid();
            urlImage = ((ModelUser) user).getImage();
            String str = ((ModelUser) user).getEmail();
            String substring = str.substring(0, str.lastIndexOf("@"));
            name = substring;
        }
        myUid = firebaseUser.getUid();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setup(view);
        textViewName.setText(name);
        try {
            Picasso.get().load(urlImage).placeholder(R.drawable.logototers).into(imageViewUser);
        } catch (Exception e) {
        }

        imageSend.setOnClickListener(view1 -> {
            if (!TextUtils.isEmpty(editText.getText().toString().trim())) {
                sendMessage(editText.getText().toString().trim());
            }
        });

        adapterChat = new AdapterChat(getActivity(), chatsUserList, urlImage);
        recyclerView.setAdapter(adapterChat);
        readMessages();

        super.onViewCreated(view, savedInstanceState);
    }

    private void readMessages() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatsUserList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ChatsUser chat = ds.getValue(ChatsUser.class);
                   // if (chat.getReceiver().equals(myUid) && chat.getSender().equals(userUid) ||
                        //    chat.getReceiver().equals(userUid) && chat.getSender().equals(myUid)) {
                        chatsUserList.add(chat);

                        

                        adapterChat.notifyDataSetChanged();
                   // }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String msg) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", myUid);
        hashMap.put("receiver", userUid);
        hashMap.put("message", msg);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
        databaseReference.child("Chats").push().setValue(hashMap);
        editText.setText("");

    }

    private void setup(View view) {
        toolbar = view.findViewById(R.id.toolbar_chat);
        imageViewUser = view.findViewById(R.id.image_chat);
        textViewName = view.findViewById(R.id.email_chat);
        recyclerView = view.findViewById(R.id.recyleview_chat);
        editText = view.findViewById(R.id.text_chat);
        imageSend = view.findViewById(R.id.send_chat);

    }


}

