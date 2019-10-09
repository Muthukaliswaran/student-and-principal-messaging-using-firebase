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

public class MessageSentAdapter extends RecyclerView.Adapter<MessageSentAdapter.MyViewHolder> {
    Context context;
    List<Datasnapshot_Model> dataList;
    String FromTo;

    public MessageSentAdapter(Context context, List<Datasnapshot_Model> dataList,String FromTo) {
    this.context=context;
    this.dataList=dataList;
    this.FromTo=FromTo;

    }

    @NonNull
    @Override
    public MessageSentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_adapter, parent, false);
        return new MessageSentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageSentAdapter.MyViewHolder holder, int position) {
        holder.txt_FromTo.setText(FromTo);
        holder.title.setText(dataList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_FromTo,title;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_FromTo=itemView.findViewById(R.id.txt_FromTo);
            title=itemView.findViewById(R.id.title);
        }
    }
}
