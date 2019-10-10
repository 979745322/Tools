package com.lyg.tools.diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.lyg.tools.R;
import com.lyg.tools.utils.FileUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DiaryList extends AppCompatActivity {
    private ListView listView;
    private MyAdapter adapter;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_list);
        setTitle("日记");
        listView = findViewById(R.id.diaryList);
        list = new ArrayList<>();
        List<File> fileList = FileUtil.listFileSortByModifyTime(getFilesDir().toString());
        for (File file : fileList) {
            if (!file.getName().contains("reload0x0000")) {
                list.add(file.getName());
            }
        }
        adapter = new MyAdapter(DiaryList.this, list);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.diarylistmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, Password.class);
        intent.putExtra("struts", "addPwd");
        if (item.getItemId() == R.id.diaryaddpwd) {
            startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
    }

    public void diaryAdd(View view) {
        startActivity(new Intent(this, DiaryEdit.class));
        finish();
    }

}
