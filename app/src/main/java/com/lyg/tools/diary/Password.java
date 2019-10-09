package com.lyg.tools.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lyg.tools.R;

public class Password extends AppCompatActivity {

    private SharedPreferences sp;
    private String pwd;
    private EditText editText;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        editText = findViewById(R.id.editText);

        // 检查是否有密码文件，没有则创建
        sp = getSharedPreferences("diary", Context.MODE_APPEND);
        pwd = sp.getString("password","");

        if(pwd.equals("")){ // 如果没有设置密码，直接进入日记列表页面
            // 进日记列表页面
            startActivity(new Intent(this,DiaryList.class));
        }
    }

    public void login(View view) {

        if(pwd.equals(editText.getText().toString())){ // 密码正确
            // 进日记列表页面
            startActivity(new Intent(this,DiaryList.class));
        }else{ // 密码错误
            editText.setText("");
            Toast.makeText(this,"密码输入错误，请重新输入！！！",Toast.LENGTH_SHORT).show();
        }
    }
}
