package com.liujiating.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.liujiating.model.DiaryUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jia on 2016/4/4.
 */
public class CloudNoteDB {
    /**
     * 数据库名
     */
    public static final String DB_NAME = "CloudNote.db";
    /**
     * 数据库版本
     */
    public static final int VERSION = 1;

    private static CloudNoteDB cloudNoteDB;

    private SQLiteDatabase db;

    /**
     * 将构造函数私有化
     */
    public CloudNoteDB(Context context) {
        CloudNoteOpenHelper helper = new CloudNoteOpenHelper(context, DB_NAME, null, VERSION);
        db=helper.getWritableDatabase();
    }

    /**
     * 获取CloudNote实例,同时保证全局仅有一个实例
     */
    public synchronized static CloudNoteDB getInstance(Context context) {
        if (cloudNoteDB == null) {
            cloudNoteDB = new CloudNoteDB(context);
        }
        return cloudNoteDB;
    }


    /**
     * 将DiaryUtil实例储存到数据库
     */
    public void saveDiary(DiaryUtil diary) {
        if (diary != null) {
            ContentValues values = new ContentValues();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//注意大小写,MM表示月份,mm表示分钟
            values.put("diary_content", diary.getContent());
            values.put("diary_date", format.format(diary.getDate()));

            if(diary.getTitle().equals("")){
                values.put("diary_title","无标题");
            }else{
                values.put("diary_title",diary.getTitle());
            }

            values.put("diary_weather",diary.getWeatherIconId());
            db.insert("diary", null, values);
        }
    }

    /**
     * 删除日记
     * @param diaryId
     */
    public void DeleteDiary(int diaryId){

        db.delete("diary","id=?",new String[]{String.valueOf(diaryId)});
    }

    public void UpdateDiary(int diaryId,ContentValues values){
        db.update("diary",values,"id=?",new String[]{String.valueOf(diaryId)});
    }


    public List<DiaryUtil> loadDiary() {

        List<DiaryUtil> list = new ArrayList<>();
        Cursor cursor = db.query("diary", null, null, null, null, null, null);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        /**
         * 遍历整张表,取出表中数据设置给DiaryUtil实例对应的成员变量
         */
        if (cursor.moveToFirst()) {
            do {
                DiaryUtil diary = new DiaryUtil();
                //读取正文
                diary.setContent(cursor.getString(cursor.getColumnIndex("diary_content")));

                //将数据库中date类型从String转为Date类型，抛出异常声明
                 
                try {
                    diary.setDate(format.parse(cursor.getString(cursor.getColumnIndex("diary_date"))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //读取标题
                diary.setTitle(cursor.getString(cursor.getColumnIndex("diary_title")));

                //读取图片id
                diary.setWeatherIconId(cursor.getInt(cursor.getColumnIndex("diary_weather")));

                //读取日记id
                diary.setDiaryId(cursor.getInt(cursor.getColumnIndex("id")));

                list.add(diary);
            } while (cursor.moveToNext());

        }

            return list;
    }





}
