package com.example.photori.photoribook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.List;

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

        calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                items.clear();
                String s="";
                char [] date_array=date.toString().toCharArray();
                s=s+date_array[12]+date_array[13]+date_array[14]+date_array[15]+"."+date_array[17]+(int)date_array[18]+"."+date_array[20]+date_array[21];
                 ParseUser user=ParseUser.getCurrentUser();
                ParseRelation<ParseObject> relation=user.getRelation("My_memory");
                ParseQuery<ParseObject> query=relation.getQuery();
                query.whereEqualTo("Time",s);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, ParseException e) {
                        for(ParseObject o:list){
                            byte[] bytes= new byte[10];
                            ParseFile file=(ParseFile)o.get("Photo");
                            try {
                                bytes =file.getData();
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                            CalendarPhotoItem item = new CalendarPhotoItem(bytes);
                            items.add(item);
                        }
                        calendar_photo.setAdapter(new CalendarPhotoAdapter(getApplicationContext(),items));

                    }
                });
            }
        });


    }
}
