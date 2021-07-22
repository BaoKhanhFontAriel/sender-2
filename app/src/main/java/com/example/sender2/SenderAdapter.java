package com.example.sender2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SenderAdapter extends RecyclerView.Adapter<SenderAdapter.SenderViewHolder> {

    ArrayList<MessageEntry> messagesList;

    public SenderAdapter(ArrayList<MessageEntry> messagesList){
        this.messagesList = messagesList;
    }

    @Override
    public SenderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View senderView = inflater.inflate(R.layout.message_ui, parent, false);

        // Return a new holder instance
        return new SenderViewHolder(senderView);
    }

    @Override
    public void onBindViewHolder(SenderAdapter.SenderViewHolder holder, int position) {
        MessageEntry messageEntry = messagesList.get(position);

        if (!messageEntry.getSenderMessage().isEmpty() && messageEntry.getReceiverMessage().isEmpty() ) {
            holder.senderMessage.setText(messageEntry.getSenderMessage());
            holder.receiverMessage.setBackground(null);
        }
        if (messageEntry.getSenderMessage().isEmpty() && !messageEntry.getReceiverMessage().isEmpty() ) {
            holder.senderMessage.setBackground(null);
            holder.receiverMessage.setText(messageEntry.getReceiverMessage());
        }
        if (messageEntry.getSenderMessage().isEmpty() && messageEntry.getReceiverMessage().isEmpty()) {
            holder.senderMessage.setVisibility(View.GONE);
            holder.receiverMessage.setVisibility(View.GONE);
        }


    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView senderMessage;
        TextView receiverMessage;

        public SenderViewHolder(View itemView) {
            super(itemView);

            senderMessage = itemView.findViewById(R.id.send_message);
            receiverMessage = itemView.findViewById(R.id.receive_message);
        }
    }

    public void insertItem(MessageEntry message){
        this.messagesList.add(message);
        notifyItemInserted(messagesList.size());
    }
}
