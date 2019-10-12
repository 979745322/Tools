package com.lyg.tools.joke;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.erning.common.utils.LogUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.lyg.tools.R;
import com.lyg.tools.entity.Joke;
import com.lyg.tools.entity.Joke_Table;
import com.lyg.tools.entity.OttoStringData;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

public class JokeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ImageRequest imageRequest;
    private AbstractDraweeController<?,?> draweeController;
    private Context context;
    private ArrayList<Joke> arrayList;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context,ImageActivity.class);
            intent.putExtra("text","");
            intent.putExtra("imgs",new String[]{
                    arrayList.get((Integer) view.getTag()).getImages()
            });
            context.startActivity(intent);

        }
    };

    private View.OnClickListener onClickImgListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            view.setVisibility(View.INVISIBLE);
            JokeHolderVideo jhv = (JokeHolderVideo)view.getTag();
            jhv.videoView.requestFocus();
            jhv.videoView.start();
        }
    };

    private VideoView.OnClickListener onClickVideoListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(((VideoView) view).isPlaying()){
                ((VideoView) view).requestFocus();
                ((VideoView) view).pause();
            }else{
                ((VideoView) view).requestFocus();
                ((VideoView) view).start();
            }

        }
    };

    private View.OnClickListener onClickBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(arrayList.get((Integer) view.getTag()).getId()==null){
                arrayList.get((Integer) view.getTag()).save();
                Toast.makeText(context,"收藏成功",Toast.LENGTH_SHORT).show();
            }else{
                SQLite.delete().from(Joke.class).where(Joke_Table.id.eq(arrayList.get((Integer) view.getTag()).getId())).query();
                int index = (Integer) view.getTag();
                arrayList.remove(index);
                Toast.makeText(context,"取消收藏成功",Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }

        }
    };

    public JokeAdapter(Context context, ArrayList<Joke> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (arrayList.get(position).getType()) {
            case "video":
                return 1;
            case "gif":
                return 2;
            case "image":
                return 2;
            case "text":
                return 3;
        }
        return 0;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new JokeHolderVideo(LayoutInflater.from(context).inflate(R.layout.item_joke_video, parent, false));
            case 2:
                return new JokeHolderImg(LayoutInflater.from(context).inflate(R.layout.item_joke_img, parent, false));
            case 3:
                return new JokeHolderText(LayoutInflater.from(context).inflate(R.layout.item_joke_text, parent, false));
            default:
                return new JokeHolderText(LayoutInflater.from(context).inflate(R.layout.item_joke_text, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof JokeHolderText) {
            ((JokeHolderText) holder).textView.setText(arrayList.get(position).getText());
            ((JokeHolderText) holder).imageBtnView.setTag(position);
            ((JokeHolderText) holder).imageBtnView.setOnClickListener(onClickBtnListener);
        }else if(holder instanceof JokeHolderVideo){
            ((JokeHolderVideo) holder).textView.setText(arrayList.get(position).getText());
            ((JokeHolderVideo) holder).videoView.setVideoPath(arrayList.get(position).getVideo());
            ((JokeHolderVideo) holder).imageView.setImageURI(arrayList.get(position).getThumbnail());
            ((JokeHolderVideo) holder).imageView.setTag(holder);
            ((JokeHolderVideo) holder).imageView.setOnClickListener(onClickImgListener);
            ((JokeHolderVideo) holder).videoView.setOnClickListener(onClickVideoListener);
            ((JokeHolderVideo) holder).imageBtnView.setTag(position);
            ((JokeHolderVideo) holder).imageBtnView.setOnClickListener(onClickBtnListener);
        } else if (holder instanceof JokeHolderImg) {
            ((JokeHolderImg) holder).textView.setText(arrayList.get(position).getText());
//            ((JokeHolderImg) holder).imageView.setImageURI(arrayList.get(position).getImages());
            LogUtils.d("URL",arrayList.get(position).getImages());
            imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(arrayList.get(position).getImages())).setResizeOptions(new ResizeOptions(500, 500)).setLocalThumbnailPreviewsEnabled(true).build();
            draweeController = Fresco.newDraweeControllerBuilder().setOldController(((JokeHolderImg) holder).imageView.getController()).setImageRequest(imageRequest).setAutoPlayAnimations(true).build();
            ((JokeHolderImg) holder).imageView.setController(draweeController);
            ((JokeHolderImg) holder).imageView.setTag(position);
            ((JokeHolderImg) holder).imageView.setOnClickListener(onClickListener);
            ((JokeHolderImg) holder).imageBtnView.setTag(position);
            ((JokeHolderImg) holder).imageBtnView.setOnClickListener(onClickBtnListener);

        }

        // 滑动到最后一个加载新数据
        if(position == arrayList.size()-1){
            AppBus.instance.post(new OttoStringData("load"));
        }

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void addData(List<Joke> list) {
        if(list.size()!=0) {
            arrayList.addAll(list);
            LogUtils.d("List", String.valueOf(arrayList.size()));
            notifyDataSetChanged();
        }
    }

}
