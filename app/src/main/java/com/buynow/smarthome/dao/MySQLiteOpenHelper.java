package com.buynow.smarthome.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Zhao on 2016/7/17.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    public final static String SQL_NAME = "SmartHome.db";
    public final static int VERSION = 1;
    public final static String STUDYMOD_TABLE_NAME = "StudyMod";
    //创建学习模块表
    public static final String CREATE_STUDYMOD = "create table "+STUDYMOD_TABLE_NAME+" ("
            + "id integer primary key autoincrement, "
            + "name text, "
            + "count integer)";
    public MySQLiteOpenHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, SQL_NAME, factory, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDYMOD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+STUDYMOD_TABLE_NAME);
        onCreate(db);
    }
}
