package com.prasanthkalis.gce_bodi.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prasanthkalis.gce_bodi.R;
import com.prasanthkalis.gce_bodi.adapter.MessageReceivedAdapter;
import com.prasanthkalis.gce_bodi.databinding.FragmentMsgReceivedBinding;
import com.prasanthkalis.gce_bodi.model.Datasnapshot_Model;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Message_StudentReceivedFragment extends Fragment {
    FragmentMsgReceivedBinding binding;
    DatabaseReference rootRef;
    List<Datasnapshot_Model> databaseData = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_msg_received,container,false);
        messageReceived();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        binding.recyMessage.setLayoutManager(linearLayoutManager);

        return binding.getRoot();
    }
    public  void messageReceived(){
        rootRef = FirebaseDatabase.getInstance().getReference().child("principal");
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                databaseData.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Datasnapshot_Model datasnapshot_model = postSnapshot.getValue(Datasnapshot_Model.class);

                    Log.d(TAG, "onDataChange: " + datasnapshot_model);
                    databaseData.add(datasnapshot_model);
                    Log.d(TAG, "onDataChange: " + databaseData);
                }
                Log.d(TAG, "onDataChange: " + databaseData.size());
                binding.recyMessage.setAdapter(new MessageReceivedAdapter(getContext(),databaseData,"From: Principal GCE"));

                for (int i = 0; i < databaseData.size(); i++) {
                    Log.d(TAG, "onDataChange: " + databaseData.get(i).getBody());
                    Log.d(TAG, "onDataChange: " + databaseData.get(i).getTitle());

                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });

    }
}
