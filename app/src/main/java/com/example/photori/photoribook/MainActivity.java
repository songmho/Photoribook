package com.example.photori.photoribook;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerlayout;
    NavigationView navigationView;

    Toolbar toolbar;

    RecyclerView recyclerView;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences shpref=getSharedPreferences("myPref",0);
        //앱 종류 여부에 상관하지 않고 값을 저장하고 싶을때
       /* int count=shpref.getInt("Count",-100);
        if(count==-100){
            startActivity(new Intent(MainActivity.this, SplashActivity.class));
            count=1;
        }
        else {
            count++;
        }SharedPreferences.Editor editor=shpref.edit();
        editor.putInt("Count",count);
        editor.commit();
*/
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


        ArrayList<CardItem> items=new ArrayList<>();
        for(int i=0;i<5;i++){
            Date d=new Date();
            SimpleDateFormat f=new SimpleDateFormat("yyyy.MM.dd");
            CardItem item=new CardItem(0,false,f.format(d).toString(),"test");
            items.add(item);
        }

        recyclerView.setHasFixedSize(true);                             //리사이클러뷰 화면에 고정
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new CardAdapter(getApplicationContext(),items));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "명호설사똥바보", Toast.LENGTH_SHORT).show();
            }
        });
    }   //onCreate

    private boolean clickDrawerMenu(MenuItem menuItem) {
        return false;
    }
}
