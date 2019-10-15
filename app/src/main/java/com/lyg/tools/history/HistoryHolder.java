package com.lyg.tools.history;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lyg.tools.R;

public class HistoryHolder extends RecyclerView.ViewHolder {
    public TextView history_date;
    public TextView history_title;
    public HistoryHolder(@NonNull View itemView) {
        super(itemView);
        history_date = itemView.findViewById(R.id.history_date);
        history_title = itemView.findViewById(R.id.history_title);
    }
}
