package com.lyg.tools.joke;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lyg.tools.R;

public class JokeHolderText extends RecyclerView.ViewHolder{
    public TextView textView;
    public ImageView imageBtnView;
    public JokeHolderText(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.joke_text);
        imageBtnView = itemView.findViewById(R.id.joke_btn_collect);
    }

}
