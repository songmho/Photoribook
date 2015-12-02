package com.example.photori.photoribook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

public class CalendarFragment extends Fragment {
    MaterialCalendarView calendar;
    RecyclerView calendar_photo;

    ArrayList<CalendarPhotoItem> items=new ArrayList<>();

    LinearLayout cur_layout;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        cur_layout=(LinearLayout)inflater.inflate(R.layout.fragment_calendar,container,false);

        Log.d("dddd","DDD");

        calendar=(MaterialCalendarView)cur_layout.findViewById(R.id.calendar);
        calendar_photo=(RecyclerView)cur_layout.findViewById(R.id.calendar_photo);

        calendar_photo.setHasFixedSize(true);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);

        calendar_photo.setLayoutManager(layoutManager);

        calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                items.clear();
                String s="";
                if(date.getMonth()+1>12) {
                    s=""+(date.getYear()+1)+"."+1+"."+date.getDay();
                }
                else{
                    s=""+date.getYear()+"."+(date.getMonth()+1)+"."+date.getDay();
                }
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
                        calendar_photo.setAdapter(new CalendarPhotoAdapter(getActivity().getApplicationContext(),items));

                    }
                });
            }
        });




        return cur_layout;
    }
}
