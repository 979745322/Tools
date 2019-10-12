package com.lyg.tools.joke;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lyg.tools.R;

public class JokeHolderImg extends RecyclerView.ViewHolder{
    public TextView textView;
    public SimpleDraweeView imageView;
    public ImageView imageBtnView;
    public JokeHolderImg(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.joke_text);
        imageView = itemView.findViewById(R.id.joke_img);
        imageBtnView = itemView.findViewById(R.id.joke_btn_collect);
    }
}
