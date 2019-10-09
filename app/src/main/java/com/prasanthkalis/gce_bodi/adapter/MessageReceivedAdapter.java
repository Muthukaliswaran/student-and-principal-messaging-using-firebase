package com.prasanthkalis.gce_bodi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prasanthkalis.gce_bodi.R;
import com.prasanthkalis.gce_bodi.model.Datasnapshot_Model;

import java.util.List;

public class MessageReceivedAdapter extends RecyclerView.Adapter<MessageReceivedAdapter.MyviewHolder> {
    List<Datasnapshot_Model> databaseData;
    Context context;
    String FromTo;
    public MessageReceivedAdapter(Context context, List<Datasnapshot_Model> databaseData,String FromTo) {
    this.context=context;
    this.databaseData=databaseData;
    this.FromTo=FromTo;

    }

    @NonNull
    @Override
    public MessageReceivedAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_adapter, parent, false);
        return new MyviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageReceivedAdapter.MyviewHolder holder, int position) {
           holder.txt_FromTo.setText(FromTo);
           holder.title.setText(databaseData.get(position).getTitle());
         //  holder.body.setText(databaseData.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return databaseData.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView txt_FromTo,title;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            txt_FromTo=itemView.findViewById(R.id.txt_FromTo);
            title=itemView.findViewById(R.id.title);
        }
    }
}
