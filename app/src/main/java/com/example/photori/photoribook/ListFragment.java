package com.example.photori.photoribook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songmho on 15. 11. 29..
 */
public class ListFragment extends Fragment {

    LinearLayout cur_layout;
    RecyclerView recyclerView;
    ArrayList<CardItem> items=new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        cur_layout=(LinearLayout)inflater.inflate(R.layout.fragment_list,container,false);
        recyclerView=(RecyclerView)cur_layout.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);                             //리사이클러뷰 화면에 고정
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        items.clear();

        final byte[][] bytes = {new byte[10]};

        ParseUser u=ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query= u.getRelation("My_memory").getQuery();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(list.size()>0 && list!=null) {
                    for (ParseObject o : list) {
                        ParseFile file=(ParseFile)o.get("Photo");
                        try {
                            bytes[0] =file.getData();
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }

                        CardItem item = new CardItem(o.getString("objectId"), bytes[0], o.getBoolean("isFamous"), o.getString("Time"),
                                o.getString("Title"), o.getString("Detail"));

                        items.add(item);
                    }
                    recyclerView.setAdapter(new CardAdapter(getActivity().getApplicationContext(), items, R.layout.activity_main));
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "리스트가 없습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return cur_layout;
    }
}
