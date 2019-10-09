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
import com.prasanthkalis.gce_bodi.adapter.MessageSentAdapter;
import com.prasanthkalis.gce_bodi.databinding.FragmentMsgSentBinding;
import com.prasanthkalis.gce_bodi.model.Datasnapshot_Model;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Message_StudentSentFragment extends Fragment {
    FragmentMsgSentBinding binding;
    DatabaseReference rootRef;
    List<Datasnapshot_Model> studentData = new ArrayList<>();
    String mob;

    public Message_StudentSentFragment(String mob) {
        this.mob=mob;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_msg_sent,container,false);
        messageReceived();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        binding.recyMessage.setLayoutManager(linearLayoutManager);

        return binding.getRoot();

    }

    public  void messageReceived() {
        rootRef = FirebaseDatabase.getInstance().getReference().child("students/"+mob);
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                studentData.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Datasnapshot_Model datasnapshot_model = postSnapshot.getValue(Datasnapshot_Model.class);

                    Log.d(TAG, "onDataChange: " + datasnapshot_model);
                    studentData.add(datasnapshot_model);
                    Log.d(TAG, "onDataChange: " + studentData);

                }
                Log.d(TAG, "onDataChange: " + studentData.size());
                binding.recyMessage.setAdapter(new MessageSentAdapter(getContext(), studentData,"To: Principal GCE"));

                for (int i = 0; i < studentData.size(); i++) {
                    Log.d(TAG, "onDataChange: " + studentData.get(i).getBody());
                    Log.d(TAG, "onDataChange: " + studentData.get(i).getTitle());

                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });

    }
}
