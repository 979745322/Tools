package com.lyg.tools.joke;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lyg.tools.R;

public class JokeHolderVideo extends RecyclerView.ViewHolder{
    public TextView textView;
    public SimpleDraweeView imageView;
    public VideoView videoView;
    public ImageView imageBtnView;
    public JokeHolderVideo(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.joke_text);
        imageView = itemView.findViewById(R.id.joke_img);
        videoView = itemView.findViewById(R.id.joke_video);
        imageBtnView = itemView.findViewById(R.id.joke_btn_collect);
    }
}
