package com.example.schedule.diary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chenhui on 2017/5/26.
 * All Rights Reserved by YiZu
 * 数据库帮助类
 */

public class DbOpenHelper extends SQLiteOpenHelper{

    public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 创建数据库
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSql = "CREATE TABLE Diary(\n" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "date NVARCHAR(20),\n" +
                "content NVARCHAR(1000),\n" +
                "week NVARCHAR(20),\n" +
                "weather NVARCHAR(20));";
        db.execSQL(createSql);
    }

    /**
     *更新数据库表结构
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}