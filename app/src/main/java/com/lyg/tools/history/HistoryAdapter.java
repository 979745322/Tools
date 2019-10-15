package com.lyg.tools.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.erning.common.utils.LogUtils;
import com.lyg.tools.R;
import com.lyg.tools.history.ebtity.History;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<History> arrayList;
    private Context context;

    public HistoryAdapter(ArrayList<History> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryHolder(LayoutInflater.from(context).inflate(R.layout.item_history_text, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((HistoryHolder) holder).history_date.setText(arrayList.get(position).getDate());
        ((HistoryHolder) holder).history_title.setText(arrayList.get(position).getTitle());
        LogUtils.d("data", arrayList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void addData(List<History> list) {
        if (list.size() != 0) {
            arrayList.addAll(list);
            LogUtils.d("arrayList", arrayList.toString());
            LogUtils.d("List", String.valueOf(arrayList.size()));
            notifyDataSetChanged();
        }
    }

    public void setData(List<History> list) {
        arrayList.clear();
        arrayList.addAll(list);
        LogUtils.d("arrayList", arrayList.toString());
        LogUtils.d("List", String.valueOf(arrayList.size()));
        notifyDataSetChanged();
    }
}
