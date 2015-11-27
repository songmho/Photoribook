package com.example.photori.photoribook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by songmho on 15. 11. 26..
 */
public class ShotActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;
    ArrayList<CardItem> items=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("인생샷보기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);                             //리사이클러뷰 화면에 고정
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
   }

    @Override
    protected void onResume() {
        super.onResume();
        items.clear();
        Date d=new Date();
        SimpleDateFormat f=new SimpleDateFormat("yyyy.MM.dd");

        ParseUser u=ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query= u.getRelation("My_memory").getQuery();
        query.whereEqualTo("isFamous",true);
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
                else{
                    Toast.makeText(ShotActivity.this, "리스트가 없습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
