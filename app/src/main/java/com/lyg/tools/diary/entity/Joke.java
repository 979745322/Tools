package com.lyg.tools.diary.entity;

import com.lyg.tools.db.DBFlowDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = DBFlowDataBase.class) //上面自己创建的类（定义表的名称 版本）
public class Joke extends BaseModel {
    @PrimaryKey(autoincrement = true) //主键  //autoincrement 开启自增
    private Long id;
    @Column
    private Long sid;
    @Column               //表示一栏 一列
    private String text;
    @Column               //表示一栏 一列
    private String type;
    @Column               //表示一栏 一列
    private String thumbnail;
    @Column               //表示一栏 一列
    private String video;
    @Column               //表示一栏 一列
    private String images;
    @Column               //表示一栏 一列
    private String top_comments_content;


    public Joke() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getTop_comments_content() {
        return top_comments_content;
    }

    public void setTop_comments_content(String top_comments_content) {
        this.top_comments_content = top_comments_content;
    }

    @Override
    public String toString() {
        return "Joke{" +
                "id=" + id +
                ", sid=" + sid +
                ", text='" + text + '\'' +
                ", type='" + type + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", video='" + video + '\'' +
                ", images='" + images + '\'' +
                ", top_comments_content='" + top_comments_content + '\'' +
                '}';
    }
}
