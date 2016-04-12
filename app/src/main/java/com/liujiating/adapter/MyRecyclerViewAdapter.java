package com.liujiating.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.liujiating.cloudnote.R;
import com.liujiating.model.DiaryUtil;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;

/**
 * Created by jia on 2016/4/2.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<DiaryUtil> mData;

    OnRecyclerItemClickListener mListener;
    /**
     *
     * 构造方法
     * @return
     */

    public MyRecyclerViewAdapter(Context context,List<DiaryUtil> data){
        this.mContext=context;
        this.mInflater=LayoutInflater.from(context);
        this.mData=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.cardview,parent,false),mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        initView(holder, position);
        initEvent(holder,position);

    }

    /**
     * 初始化View
     */
    public  void initView(MyViewHolder holder, int position){
        //设置标题
        String title=mData.get(position).getTitle();
        if(title.equals("")){
            holder.mDiaryTitle.setText("无标题");
        }else{
            holder.mDiaryTitle.setText(title);
        }

        //设置时间
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        holder.mDiaryDate.setText(format.format(mData.get(position).getDate()));
        //设置天气
        holder.mDiaryWeather.setImageResource(mData.get(position).getWeatherIconId());

    }

    /**
     * 初始化事件
     */
    public  void initEvent(MyViewHolder holder, int position){

    }

    /**
     * 注册接口
     */

    public void setOnItemClickListener(OnRecyclerItemClickListener listener){
        this.mListener=listener;
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    /**
     * 定义自己ViewHolder
     */
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mDiaryTitle;
        TextView mDiaryDate;
        ImageView mDiaryWeather;
        OnRecyclerItemClickListener mListener;

        public MyViewHolder(View itemView,OnRecyclerItemClickListener listener) {
            super(itemView);
            mDiaryTitle=(TextView)itemView.findViewById(R.id.diary_title);
            mDiaryDate=(TextView)itemView.findViewById(R.id.diary_date);
            mDiaryWeather=(ImageView)itemView.findViewById(R.id.diary_weather);
            //设置监听
            this.mListener=listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(v,getPosition());
        }


    }

    public interface OnRecyclerItemClickListener {
         void onItemClick(View view,int postion);
    }



}
