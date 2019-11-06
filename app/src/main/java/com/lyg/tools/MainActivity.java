package com.lyg.tools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lyg.tools.blog.editblog.EditBlogActivity;
import com.lyg.tools.blog.lookblog.LookBlogActivity;
import com.lyg.tools.diary.Password;
import com.lyg.tools.history.HistoryActivity;
import com.lyg.tools.joke.JokeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * 查看博客功能
     * @param view
     */
    public void lookBlog(View view) {
        // 进入日记密码界面
        startActivity(new Intent(this, LookBlogActivity.class));
    }
    /**
     * 编辑博客功能
     * @param view
     */
    public void editBlog(View view) {
        // 进入日记密码界面
        startActivity(new Intent(this, EditBlogActivity.class));
    }
    /**
     * 日记功能
     * @param view
     */
    public void diary(View view) {
        // 进入日记密码界面
        startActivity(new Intent(this, Password.class));
    }

    /**
     * 段子功能
     * @param view
     */
    public void joke(View view) {
        // 进入段子界面
        startActivity(new Intent(this, JokeActivity.class));
    }

    /**
     * 历史上的今天
     * @param view
     */
    public void history(View view) {
        // 进入段子界面
        startActivity(new Intent(this, HistoryActivity.class));
    }
}
