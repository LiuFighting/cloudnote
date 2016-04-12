package com.liujiating.cloudnote;


import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.liujiating.database.CloudNoteDB;
import com.liujiating.fragment.BillFragment;
import com.liujiating.fragment.DiaryFragment;

import com.liujiating.model.DiaryUtil;



import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("云日记");
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        /**
         * 初始化Bmob
         */
        Bmob.initialize(this,"59a30b6521686a80ac13c656d54f5319");


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//
//
//        //noinspection SimplifiableIfStatement
//        int id = item.getItemId();
//
//        switch (id){
//            case R.id.action_upload:
//                CloudNoteDB db=CloudNoteDB.getInstance(this);
//                List<DiaryUtil> data=new ArrayList<>();
//                data=db.loadDiary();
//                for(int i=0;i<data.size();i++){
//                    DiaryUtil diary=data.get(i);
//                    diary.save(this, new SaveListener() {
//                        @Override
//                        public void onSuccess() {
//                            Log.d("xjbd","成功");
//                        }
//
//                        @Override
//                        public void onFailure(int i, String s) {
//                            Log.d("xjbd","失败");
//                        }
//                    });
//
//                }
//                break;
//            case R.id.action_download:
//                Toast.makeText(this,"目前未添加该功能",Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.action_settings:
//                Toast.makeText(this,"版本1.1.0,作者 刘嘉挺",Toast.LENGTH_SHORT).show();
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_riji) {
            // Handle the camera action

            DiaryFragment fragment1=new DiaryFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment1).commit();
            getSupportFragmentManager().beginTransaction().addToBackStack(null);
            toolbar.setTitle("日记本");


        } else if (id == R.id.nav_jizhang) {
            BillFragment fragment2=new BillFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment2).commit();
        } else if (id == R.id.nav_beiwang) {

        } else if (id == R.id.nav_shezhi) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
