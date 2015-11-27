package com.example.photori.photoribook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerlayout;
    NavigationView navigationView;

    Toolbar toolbar;

    RecyclerView recyclerView;
    FloatingActionButton fab;

    FragmentTransaction fragmentTransaction;
    ArrayList<CardItem> items=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences shpref=getSharedPreferences("myPref",0);
        //앱 종료 여부에 상관하지 않고 값을 저장하고 싶을때
        int count=shpref.getInt("Count",-100);
        if(ParseUser.getCurrentUser()==null){
            startActivity(new Intent(MainActivity.this, SplashActivity.class));
            finish();
            count=1;
        }
        else {
            count++;
        }SharedPreferences.Editor editor=shpref.edit();
        editor.putInt("Count",count);
        editor.commit();

        setContentView(R.layout.activity_main);

        drawerlayout=(DrawerLayout)findViewById(R.id.drawerlayout);
        navigationView=(NavigationView)findViewById(R.id.navigationView);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {        //드로어에 있는 리스트 클릭했을 때
                return clickDrawerMenu(menuItem);
            }
        });

        toolbar.setNavigationIcon(R.drawable.drawericon);           //액션바에 서랍버튼 넣기

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerlayout.openDrawer(GravityCompat.START);       //액션바에 있는 서랍버튼 눌렀을 때 드로어 열기
            }
        });

        TextView name = (TextView) navigationView.findViewById(R.id.name);
        ImageView profile=(ImageView)navigationView.findViewById(R.id.profile);
        byte[] bytes=new byte[10];
        Glide.with(getApplicationContext()).load(R.drawable.ss).
                bitmapTransform(new CropCircleTransformation(navigationView.getContext())).into(profile);

        name.setText(ParseUser.getCurrentUser().getString("name"));


        recyclerView.setHasFixedSize(true);                             //리사이클러뷰 화면에 고정
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "명호설사똥바보", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });
    }   //onCreate


    @Override
    protected void onResume() {
        super.onResume();
        items.clear();

        Date d=new Date();
        SimpleDateFormat f=new SimpleDateFormat("yyyy.MM.dd");



        ParseUser u=ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query= u.getRelation("My_memory").getQuery();
        query.whereEqualTo("Time",f.format(d).toString());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(list.size()>0 && list!=null) {
                    for (ParseObject o : list) {
                        CardItem item = new CardItem(0, o.getBoolean("isFamous"), o.getString("Time"),
                                o.getString("Title"), o.getString("Detail"));
                        items.add(item);
                    }
                    recyclerView.setAdapter(new CardAdapter(getApplicationContext(), items, R.layout.activity_main));
                }
                else{  CardItem item = new CardItem(R.drawable.edit_default,false,"2015.11.27",
                        "추억을 남겨보세요", "오늘의 추억에 대해서 말해주세요");
                    items.add(item);
                recyclerView.setAdapter(new CardAdapter(getApplicationContext(), items, R.layout.activity_main));
            }
                    Toast.makeText(MainActivity.this, "리스트가 없습니다", Toast.LENGTH_SHORT).show();
                }

        });
    }

    private boolean clickDrawerMenu(MenuItem menuItem) {
        if(menuItem.getGroupId()==R.id.group_main){
            navigationView.getMenu().setGroupCheckable(R.id.group_main, true, true);
            navigationView.getMenu().setGroupCheckable(R.id.group_mypage, false, true);
            navigationView.getMenu().setGroupCheckable(R.id.group_setup, false, true);
        }
        else if(menuItem.getGroupId()==R.id.group_mypage){
            navigationView.getMenu().setGroupCheckable(R.id.group_setup, false, true);
            navigationView.getMenu().setGroupCheckable(R.id.group_mypage, true, true);
            navigationView.getMenu().setGroupCheckable(R.id.group_main, false, true);
        }
        else if(menuItem.getGroupId()==R.id.group_setup){
            navigationView.getMenu().setGroupCheckable(R.id.group_setup, true, true);
            navigationView.getMenu().setGroupCheckable(R.id.group_mypage, false, true);
            navigationView.getMenu().setGroupCheckable(R.id.group_main, false, true);
        }

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        menuItem.setChecked(true);
        switch (menuItem.getItemId()){
            case R.id.item_main:
                drawerlayout.closeDrawers();
                return true;
            case R.id.item_main2:
                startActivity(new Intent(MainActivity.this,MemoryActivity.class));
                drawerlayout.closeDrawers();
                return true;
            case R.id.item_main3:
                startActivity(new Intent(MainActivity.this,ShotActivity.class));
                drawerlayout.closeDrawers();
                return true;
            case R.id.item_mypage:
                startActivity(new Intent(MainActivity.this,MypageActivity.class));
                drawerlayout.closeDrawers();
                return true;
            case R.id.setup:
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
                drawerlayout.closeDrawers();
                return true;
            }


        return false;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (drawerlayout.isDrawerOpen(navigationView))
                    drawerlayout.closeDrawers();
                else {
                    moveTaskToBack(true);
                    finish();
                }
                break;
        }

        return true;
    }

}
