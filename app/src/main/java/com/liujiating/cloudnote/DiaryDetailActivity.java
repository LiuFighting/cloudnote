package com.liujiating.cloudnote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.liujiating.database.CloudNoteDB;

/**
 * Created by jia on 2016/4/8.
 */
public class DiaryDetailActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar mToolbar;
    TextView mTitle;
    TextView mDate;
    TextView mContent;

    ImageView mWeather;
    ImageView mDelete;
    ImageView mModify;
    ImageView mShare;
    int diary_id=0;
    public static final int color_button_press=0x595959;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_detail);
        initView();
        initEvent();
        Intent it=getIntent();
        handleIntentExtra(it);

    }



    public void handleIntentExtra(Intent intent){
        mTitle.setText(intent.getStringExtra("diary_title"));
        mDate.setText(intent.getStringExtra("diary_date"));
        mWeather.setImageResource(intent.getIntExtra("diary_weather", R.mipmap.daqintian));
        mContent.setText(intent.getStringExtra("diary_content"));
        diary_id=intent.getIntExtra("diary_id",0);
    }


    /**
     * 初始化控件
     */
    public void initView(){
        mToolbar=(Toolbar)findViewById(R.id.diary_detail_toolbar);
        mTitle=(TextView)findViewById(R.id.diary_detail_title);
        mDate=(TextView)findViewById(R.id.diary_detail_date);
        mContent=(TextView)findViewById(R.id.diary_detail_content);

        mWeather=(ImageView)findViewById(R.id.diary_detail_weather);
        mDelete=(ImageView)findViewById(R.id.ib_diary_shanchu);
        mModify=(ImageView)findViewById(R.id.ib_diary_xiugai);
        mShare=(ImageView)findViewById(R.id.ib_diary_fenxiang);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    /**
     *初始化事件
     */
    public void initEvent(){
        mDelete.setOnClickListener(this);
        mModify.setOnClickListener(this);
        mShare.setOnClickListener(this);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.diary_detail_toolbar,menu);
        return true;
    }


    @Override
    public void onClick(View v) {
        ResetImage();
        switch (v.getId()){

            case R.id.ib_diary_shanchu:
                mDelete.setImageResource(R.mipmap.icon_shanchu_select);
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setMessage("删除后无法恢复，确定删除吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent it=getIntent();
                                CloudNoteDB db=CloudNoteDB.getInstance(DiaryDetailActivity.this);
                                db.DeleteDiary(diary_id);
                                setResult(1, it);
                                Toast.makeText(DiaryDetailActivity.this,"删除成功!",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    mDelete.setImageResource(R.mipmap.icon_shanchu);
                            }
                        });
                AlertDialog dialog=builder.create();
                dialog.show();


                break;
            case R.id.ib_diary_xiugai:
                mModify.setImageResource(R.mipmap.icon_xiugai_select);
                Intent intent=new Intent(this,EditDiaryActivity.class);
                intent.putExtra("diary_title",mTitle.getText().toString());
                intent.putExtra("diary_content",mContent.getText().toString());
                intent.putExtra("diary_id",diary_id);
                intent.addFlags(2);//添加标志，用于区分不同activity
                startActivity(intent);
                finish();
                break;
            case R.id.ib_diary_fenxiang:
                mShare.setImageResource(R.mipmap.icon_fenxiang_select);
                Toast.makeText(this,"你点击了分享",Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public void ResetImage(){
        mDelete.setImageResource(R.mipmap.icon_shanchu);
        mModify.setImageResource(R.mipmap.icon_xiugai);
        mShare.setImageResource(R.mipmap.icon_fenxiang);
    }



}
