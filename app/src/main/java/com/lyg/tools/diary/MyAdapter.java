package com.lyg.tools.diary;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyg.tools.R;
import com.lyg.tools.utils.FileUtil;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;

    public MyAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_diary_list_list, null);
            vh = new ViewHolder();
            vh.tv_path = (TextView) convertView.findViewById(R.id.diaryName);
            vh.imageView = convertView.findViewById(R.id.diaryDelete);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        String path = list.get(position);
        File file = new File(path);
        if (file.isDirectory()) {
            vh.tv_path.setText(path);
        } else {
            vh.tv_path.setText(path);
        }

        // 点击日记查看详情
        vh.tv_path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DiaryEdit.class);
                FileInputStream inStream = null;
                try {
                    inStream = context.openFileInput(vh.tv_path.getText().toString());
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int length = -1;
                    while ((length = inStream.read(buffer)) != -1) {
                        stream.write(buffer, 0, length);
                    }
                    stream.close();
                    inStream.close();
                    intent.putExtra("btText", vh.tv_path.getText().toString());
                    intent.putExtra("conText", stream.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                context.startActivity(intent);
            }
        });

        // 删除日记
        vh.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder  = new AlertDialog.Builder(context);
                builder.setTitle("确认" ) ;
                builder.setMessage("是否删除？") ;
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FileUtil.delete(context.getFilesDir().toString() + "/" + vh.tv_path.getText().toString());
                        list.remove(position);
                        MyAdapter.this.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("否", null);
                builder.show();
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView tv_path;
        private ImageView imageView;
    }
}
