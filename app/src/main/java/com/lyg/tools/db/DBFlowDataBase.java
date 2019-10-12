package com.lyg.tools.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * 数据库配置信息
 * Created by abs on 2018/2/1.
 */

@Database(name = DBFlowDataBase.NAME, version = DBFlowDataBase.VERSION)
public class DBFlowDataBase {
    //数据库名称
    public static final String NAME = "Joke";
    //数据库版本
    public static final int VERSION = 1;
}