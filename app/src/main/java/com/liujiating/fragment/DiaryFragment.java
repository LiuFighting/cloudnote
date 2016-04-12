package com.liujiating.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.liujiating.adapter.MyRecyclerViewAdapter;
import com.liujiating.cloudnote.DiaryDetailActivity;
import com.liujiating.cloudnote.EditDiaryActivity;
import com.liujiating.cloudnote.R;
import com.liujiating.database.CloudNoteDB;
import com.liujiating.model.DiaryUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.listener.SaveListener;


/**
 * Created by jia on 2016/4/2.
 */
public class DiaryFragment extends Fragment implements MyRecyclerViewAdapter.OnRecyclerItemClickListener{

    List<DiaryUtil> mData=new ArrayList<>();
    CloudNoteDB db;
    SwipeRefreshLayout mSwiplayout;
    MyRecyclerViewAdapter mAdapter;
    RecyclerView mRecyclerView;
    FloatingActionButton fab_newDiray;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    db=CloudNoteDB.getInstance(getActivity());
                    mData=db.loadDiary();
                    Collections.reverse(mData);
                    MyRecyclerViewAdapter adapter=new MyRecyclerViewAdapter(getActivity(),mData);
                    mRecyclerView.setAdapter(adapter);
                    setAdapterListener(adapter);
                    mSwiplayout.setRefreshing(false);
                    break;
            }
        }
    };





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.diary_frgment,container,false);

        setHasOptionsMenu(true);
        initView(view);
        initEvents();
        return view;
    }

    /**
     * 初始化布局
     * @param view
     */
    public void initView(View view){
        mRecyclerView=(RecyclerView)view.findViewById(R.id.diary_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        db=CloudNoteDB.getInstance(getActivity());
        this.mData=db.loadDiary();
        Collections.reverse(mData);

        mAdapter=new MyRecyclerViewAdapter(getActivity(),mData);
        mRecyclerView.setAdapter(mAdapter);

        mSwiplayout=(SwipeRefreshLayout)view.findViewById(R.id.swip_layout);
        mSwiplayout.setColorSchemeColors(R.color.colorPrimary, R.color.colorAccent);

        fab_newDiray = (FloatingActionButton)view.findViewById(R.id.fab);

        //天气布局


    }

    public void setAdapterListener(MyRecyclerViewAdapter adapter){
        adapter.setOnItemClickListener(this);
    }

    /**
     * 初始化事件
     *
     */
    public void initEvents(){

        mSwiplayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Message message=Message.obtain();
                message.what=1;
                handler.sendMessageDelayed(message,1500);
            }
        });

        fab_newDiray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditDiaryActivity.class);
                intent.setFlags(1);
                startActivityForResult(intent, 1);

            }
        });

        this.mAdapter.setOnItemClickListener(this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 1:
                /**
                 * TODO 一问题需解决： recyclerview.adapter.notifyDataSetChanged不起作用
                 */
                this.mData=db.loadDiary();
                Collections.reverse(mData);//倒序
                MyRecyclerViewAdapter adapter=new MyRecyclerViewAdapter(getActivity(),mData);
                mRecyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(this);
        }
    }


    @Override
    public void onItemClick(View view, int postion) {
        SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");


        Intent intent=new Intent(getActivity(), DiaryDetailActivity.class);
        intent.setFlags(1);
        intent.putExtra("diary_title", mData.get(postion).getTitle());
        intent.putExtra("diary_date", f.format(mData.get(postion).getDate()));

        intent.putExtra("diary_weather", mData.get(postion).getWeatherIconId());
        intent.putExtra("diary_content", mData.get(postion).getContent());
        intent.putExtra("diary_id",mData.get(postion).getDiaryId());
        startActivityForResult(intent,1);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        //noinspection SimplifiableIfStatement
        int id = item.getItemId();

        switch (id){
            case R.id.action_upload:
                db=CloudNoteDB.getInstance(getActivity());

                for(int i=0;i<mData.size();i++){
                    DiaryUtil diary=mData.get(i);
                    diary.save(getActivity(), new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Log.d("xjbd", "成功");
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Log.d("xjbd","失败");
                        }
                    });

                }
                break;
            case R.id.action_download:
                Toast.makeText(getActivity(),"目前未添加该功能",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Toast.makeText(getActivity(),"版本1.1.0,作者 刘嘉挺",Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
