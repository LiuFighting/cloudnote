package com.liujiating.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by jia on 2016/4/4.
 */
public class CloudNoteOpenHelper extends SQLiteOpenHelper {

    private static final String CREATE_DIARY="create table diary("
                        +"id integer primary key autoincrement,"
                        +"diary_title text,"
                        +"diary_content text,"
                        +"diary_weather integer,"
                        +"diary_date text)";
    private Context mContext;

    public CloudNoteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DIARY);
        Toast.makeText(mContext,"数据库创建成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
