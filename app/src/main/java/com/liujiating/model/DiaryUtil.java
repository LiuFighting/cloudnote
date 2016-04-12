package com.liujiating.model;

import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * Created by jia on 2016/4/4.
 */
public class DiaryUtil extends BmobObject{

    private String Content;
    private Date Date;
    private int WeatherIconId;
    private String Title;
    private int DiaryId;


    public void setDiaryId(int diaryId) {
        DiaryId = diaryId;
    }

    public int getDiaryId() {

        return DiaryId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }

    public int getWeatherIconId() {
        return WeatherIconId;
    }

    public void setWeatherIconId(int weatherIconId) {
        WeatherIconId = weatherIconId;
    }
}
