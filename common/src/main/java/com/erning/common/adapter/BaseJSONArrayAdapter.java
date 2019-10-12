package com.erning.common.adapter;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by 二宁 on 2017/11/23.
 */

public abstract class BaseJSONArrayAdapter extends BaseAdapter {
    protected JSONArray dataList;
    protected Context mContext;

    public BaseJSONArrayAdapter(Context ctx, JSONArray list) {
        this.mContext = ctx;
        this.dataList = list;
    }

//    public String getString(int resid) {
//        return mContext.getResources().getString(resid);
//    }

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

    public void addData(JSONArray array){
        dataList.addAll(array);
        notifyDataSetChanged();
    }
    public void addData(JSONObject obj){
        dataList.add(obj);
        notifyDataSetChanged();
    }
    public void reSet(JSONArray array){
        dataList.clear();
        dataList.addAll(array);
        notifyDataSetChanged();
    }
    public void reSet(JSONObject obj){
        dataList.clear();
        dataList.add(obj);
        notifyDataSetChanged();
    }
    public void clear(){
        dataList.clear();
        notifyDataSetChanged();
    }
    public void replace(int index,JSONObject object){
        dataList.remove(index);
        dataList.add(index,object);
        notifyDataSetChanged();
    }
}