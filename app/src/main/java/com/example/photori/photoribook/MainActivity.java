package com.example.photori.photoribook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerlayout;
    NavigationView navigationView;

    Toolbar toolbar;

    FloatingActionButton fab;
    FragmentTransaction fragmentTransaction;

    TextView today_diary;
    TextView date;
    Button add_diary;

    Date d = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences shpref=getSharedPreferences("myPref",0);
        //앱 종료 여부에 상관하지 않고 값을 저장하고 싶을때
        int count=shpref.getInt("Count",-100);
        if(count==-100 || ParseUser.getCurrentUser()==null){
            startActivity(new Intent(MainActivity.this, SplashActivity.class));
            finish();
            count=1;
            return;
        }
        else {
            count++;
        }SharedPreferences.Editor editor=shpref.edit();
        editor.putInt("Count", count);
        editor.commit();

        setContentView(R.layout.activity_main);

        today_diary=(TextView)findViewById(R.id.today_diary);
        date=(TextView)findViewById(R.id.date);
        add_diary=(Button)findViewById(R.id.add_diary);
        add_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });

        drawerlayout=(DrawerLayout)findViewById(R.id.drawerlayout);
        navigationView=(NavigationView)findViewById(R.id.navigationView);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        date.setText(sdf.format(d));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {        //드로어에 있는 리스트 클릭했을 때
                return clickDrawerMenu(menuItem);
            }
        });

        toolbar.setNavigationIcon(R.drawable.drawericon);           //액션바에 서랍버튼 넣기
        //toolbar.setLogo(R.drawable.photoribook_logomain);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerlayout.openDrawer(GravityCompat.START);       //액션바에 있는 서랍버튼 눌렀을 때 드로어 열기
            }
        });

        TextView name = (TextView) navigationView.findViewById(R.id.name);
        ImageView profile=(ImageView)navigationView.findViewById(R.id.profile);

        String tempPath="data/data/com.example.photori.photoribook/files/profile.jpg";
        Bitmap bm = BitmapFactory.decodeFile(tempPath);
        if(bm!=null){
            Glide.with(getApplicationContext()).load(bitmapTobyte(bm)).
                    bitmapTransform(new CropCircleTransformation(getApplicationContext())).into(profile);
        }
        else{        Glide.with(getApplicationContext()).load(R.drawable.ss).
                bitmapTransform(new CropCircleTransformation(getApplicationContext())).into(profile);

        }

        name.setText(ParseUser.getCurrentUser().getString("name"));


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });
    }   //onCreate


    @Override
    protected void onResume() {
        super.onResume();
        ParseUser parseUser = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> parseQuery = parseUser.getRelation("My_memory").getQuery(); //파스오브젝트 찾기
        parseQuery.whereEqualTo("Time",sdf.format(d).toString());
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                today_diary.setText(""+list.size());
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
                startActivity(new Intent(MainActivity.this,TodayActivity.class));
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {//뒤로 가기 할때, 꺼지지 않게 하기

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


    private byte[] bitmapTobyte(Bitmap bm) {//비트맵을 바이트로 바꿔서 이미지 불러오기
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] bytes=stream.toByteArray();
        return bytes;
    }
}
