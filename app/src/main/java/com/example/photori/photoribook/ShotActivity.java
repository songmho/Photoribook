package com.example.photori.photoribook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by songmho on 15. 11. 26..
 */
public class ShotActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("인생샷보기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);

        ArrayList<CardItem> items=new ArrayList<>();
        for(int i=0;i<5;i++){
            Date d=new Date();
            SimpleDateFormat f=new SimpleDateFormat("yyyy.MM.dd");
            CardItem item=new CardItem(0,false,f.format(d).toString(),"광화문 가는 길" ,"hello\n\n\nasddf\n\n\n\n\n\n\n\n\n\n\naaaa");
            items.add(item);
        }
        recyclerView.setHasFixedSize(true);                             //리사이클러뷰 화면에 고정
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new CardAdapter(getApplicationContext(),items,R.layout.activity_shot));
    }
}
