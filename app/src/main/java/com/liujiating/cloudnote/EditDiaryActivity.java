package com.liujiating.cloudnote;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liujiating.database.CloudNoteDB;
import com.liujiating.model.DiaryUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jia on 2016/4/4.
 */
public class EditDiaryActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edit_content;
    EditText edit_title;
    Toolbar mToolbar;
    CloudNoteDB cloudNoteDB;

    ImageView iv_qintian;
    ImageView iv_duoyun;
    ImageView iv_yintian;
    ImageView iv_xiayu;
    LinearLayout weather_layout;
    LinearLayout main_layout;

     Boolean qintian_selected=false;
     Boolean duoyun_selected=false;
     Boolean yintian_selected=false;
     Boolean xiayu_selected=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_diary);

        initView();
        initEvent();
        Intent intent=getIntent();
        if(intent.getFlags()==2){
            handleDetailExtra(intent);

        }



    }

    /**
     * 处理修改日记内容
     * @param it
     */
    public void handleDetailExtra(Intent it){
        edit_title.setText(it.getStringExtra("diary_title"));
        edit_content.setText(it.getStringExtra("diary_content"));

    }

    /**
     * 事件处理
     */
    private void initEvent() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        iv_qintian.setOnClickListener(this);
        iv_duoyun.setOnClickListener(this);
        iv_yintian.setOnClickListener(this);
        iv_xiayu.setOnClickListener(this);
    }

    private void initView() {
        main_layout=(LinearLayout)findViewById(R.id.edit_diary_layout);

        mToolbar=(Toolbar)findViewById(R.id.diary_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edit_content=(EditText)findViewById(R.id.edit_diary_content);
        edit_title=(EditText)findViewById(R.id.edit_diary_title);

        weather_layout=(LinearLayout)findViewById(R.id.weather_layout);
        iv_qintian=(ImageView)findViewById(R.id.weather_qintian);
        iv_duoyun=(ImageView)findViewById(R.id.weather_duoyun);
        iv_yintian=(ImageView)findViewById(R.id.weather_yintian);
        iv_xiayu=(ImageView)findViewById(R.id.weather_xiayu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.diary_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /**
         * TODO 保存点击事件
         */
        if (getIntent().getFlags()==1){
            if(item.getItemId()==R.id.diary_complete){
                if(edit_content.getText().toString().equals("")){
                    Snackbar.make(main_layout,"什么都没写~~~",Snackbar.LENGTH_LONG)
                            .setAction("知道了", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();
                    return false;
                }else{

                   NewDiaryAction();
                }

            }
        }else if(getIntent().getFlags()==2){
            if(item.getItemId()==R.id.diary_complete){
                if(edit_content.getText().toString().equals("")){
                    Snackbar.make(main_layout,"内容不能为空~~~",Snackbar.LENGTH_LONG)
                            .setAction("知道了", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();
                    return false;
                }else{
                    int diary_id=getIntent().getIntExtra("diary_id",0);
                    ModifyDiaryAction(diary_id);
                }

            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        ResetImage();
        switch (v.getId()){
            case R.id.weather_qintian:
                iv_qintian.setImageResource(R.mipmap.qintian_select);
                qintian_selected=true;
                break;
            case R.id.weather_duoyun:
                iv_duoyun.setImageResource(R.mipmap.duoyun_select);
                duoyun_selected=true;
                break;
            case R.id.weather_yintian:
                iv_yintian.setImageResource(R.mipmap.yintian_select);
                yintian_selected=true;
                break;
            case R.id.weather_xiayu:
                iv_xiayu.setImageResource(R.mipmap.xiayu_select);
                xiayu_selected=true;
                break;

        }
    }

    /**
     * 重置图片
     */
    public void ResetImage(){
        iv_qintian.setImageResource(R.mipmap.daqintian);
        iv_duoyun.setImageResource(R.mipmap.duoyun);
        iv_yintian.setImageResource(R.mipmap.yintian);
        iv_xiayu.setImageResource(R.mipmap.xiayu);

        qintian_selected=false;
        duoyun_selected=false;
        yintian_selected=false;
        xiayu_selected=false;

    }

    /**
     * 获取天气图标id
     */
    public int getWeatherId(){

        if (qintian_selected==true){
            return R.mipmap.daqintian;
        }else if (duoyun_selected==true){
            return R.mipmap.duoyun;
        }else if (yintian_selected==true){
            return R.mipmap.yintian;
        }else if (xiayu_selected==true){
            return R.mipmap.xiayu;
        }

        return R.mipmap.daqintian;
    }

    /**
     * 新建日记响应
     */
    public void NewDiaryAction(){
        cloudNoteDB=CloudNoteDB.getInstance(this);
        DiaryUtil diary=new DiaryUtil();
        diary.setDate(new Date());
        diary.setContent(edit_content.getText().toString());
        diary.setTitle(edit_title.getText().toString());
        diary.setWeatherIconId(getWeatherId());
        cloudNoteDB.saveDiary(diary);
        Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();

        Intent intent=this.getIntent();
        setResult(1,intent);
        finish();
    }

    public void ModifyDiaryAction(int diary_id){
        cloudNoteDB=CloudNoteDB.getInstance(this);
        SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
        ContentValues values=new ContentValues();
        values.put("diary_title",edit_title.getText().toString());
        values.put("diary_content",edit_content.getText().toString());
        values.put("diary_weather",getWeatherId());
        values.put("diary_date",f.format(new Date()));
        cloudNoteDB.UpdateDiary(diary_id, values);
        Toast.makeText(this,"修改完成，刷新试试",Toast.LENGTH_SHORT).show();
        finish();
    }



}
