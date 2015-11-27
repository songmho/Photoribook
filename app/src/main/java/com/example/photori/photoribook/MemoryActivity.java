package com.example.photori.photoribook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;

public class MemoryActivity extends AppCompatActivity {
    Toolbar toolbar;
    MaterialCalendarView calendar;
    RecyclerView calendar_photo;

    ArrayList<CalendarPhotoItem> items=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);


        toolbar=(Toolbar)findViewById(R.id.toolbar);
        calendar=(MaterialCalendarView)findViewById(R.id.calendar);
        calendar_photo=(RecyclerView)findViewById(R.id.calendar_photo);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("추억꺼내기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        calendar_photo.setHasFixedSize(true);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);

        calendar_photo.setLayoutManager(layoutManager);
        CalendarPhotoItem item = new CalendarPhotoItem(R.drawable.mypage);
        items.add(item);
        CalendarPhotoItem item1 = new CalendarPhotoItem(R.drawable.mypage);
        items.add(item1);
        CalendarPhotoItem item2 = new CalendarPhotoItem(R.drawable.mypage);
        items.add(item2);
        CalendarPhotoItem item3 = new CalendarPhotoItem(R.drawable.mypage);
        items.add(item3);
        calendar_photo.setAdapter(new CalendarPhotoAdapter(getApplicationContext(),items));


    }
}
