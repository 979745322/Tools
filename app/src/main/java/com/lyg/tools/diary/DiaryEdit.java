package com.lyg.tools.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lyg.tools.R;
import com.lyg.tools.utils.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class DiaryEdit extends AppCompatActivity {

    private EditText btText;
    private EditText conText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_edit);
        setTitle("日记");
        btText = findViewById(R.id.btText);
        conText = findViewById(R.id.conText);
        // 读取日及文件标题
        if (getIntent().getStringExtra("btText") != null) {
            btText.setText(getIntent().getStringExtra("btText"));
        }
        // 读取日记文件内容
        if (getIntent().getStringExtra("conText") != null) {
            conText.setText(getIntent().getStringExtra("conText"));
        }
    }

    /**
     * 添加日记
     *
     * @param view
     */
    public void addDiary(View view) {
        String fileName = btText.getText().toString();
        String content = conText.getText().toString();
        Boolean fileExist = false;
        List<File> fileList = FileUtil.listFileSortByModifyTime(getFilesDir().toString());
        for (File file : fileList) {
            if (file.getName().equals(fileName)) {
                fileExist = true;
            }
        }
        if (fileExist&&!fileName.equals(getIntent().getStringExtra("btText"))) { // 如果文件名改变并已存在
            Toast.makeText(this, "该日记已存在，请换个标题再保存！", Toast.LENGTH_SHORT).show();
        } else if (btText.getText().toString().equals("")) { // 如果标题为空
            Toast.makeText(this, "日记标题不能为空", Toast.LENGTH_SHORT).show();
        } else {
            // 如果是编辑日记，删除之前的日记
            if (getIntent().getStringExtra("btText") != null) {
                FileUtil.delete(getFilesDir().toString() + "/" + getIntent().getStringExtra("btText"));
            }
            // 新增日记文件
            FileUtil.addFile(fileName, content, this);
            startActivity(new Intent(this, DiaryList.class));
            finish();
        }

    }
}
