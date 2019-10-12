package com.erning.common.adapter;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by 二宁 on 2017/11/23.
 */

public abstract class BaseListAdapter<T> extends BaseAdapter {
    public List<T> dataList;
    private Context mContext;

    public BaseListAdapter(Context ctx, List<T> list) {
        this.mContext = ctx;
        this.dataList = list;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected View inflate(int layout) {
        return View.inflate(mContext, layout, null);
    }

    public void addData(List<T> array){
        if (array != null){
            dataList.addAll(array);
            notifyDataSetChanged();
        }
    }

    public void setData(List<T> array){
        dataList.clear();
        dataList.addAll(array);
        notifyDataSetChanged();
    }

    public void addData(int index,List<T> array){
        if (array != null){
            dataList.addAll(index,array);
            notifyDataSetChanged();
        }
    }

    public void addData(T item){
        dataList.add(item);
        notifyDataSetChanged();
    }

    public void cleraAll(){
        dataList.clear();
        notifyDataSetChanged();
    }

    public void remove(int index){
        dataList.remove(index);
        notifyDataSetChanged();
    }
}
