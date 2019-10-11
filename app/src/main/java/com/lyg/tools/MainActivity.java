package com.lyg.tools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lyg.tools.diary.Password;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * 日记功能
     * @param view
     */
    public void diary(View view) {
        // 进入日记密码界面
        startActivity(new Intent(this, Password.class));
    }
}
