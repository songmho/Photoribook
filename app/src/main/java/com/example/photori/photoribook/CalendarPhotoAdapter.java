package com.example.photori.photoribook;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;

public class CalendarPhotoAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<CalendarPhotoItem> items;

    public CalendarPhotoAdapter(Context applicationContext, ArrayList<CalendarPhotoItem> items) {
        this.context=applicationContext;
        this.items=items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_photo, parent, false);
            return new Item(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

     //   ((Item)holder).image.setImageBitmap(BitmapFactory.decodeByteArray(items.get(position).getImage(),0,items.get(position).getImage().length));
        Glide.with(context).load(items.get(position).getImage()).into(((Item)holder).image);

        ((Item)holder).container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,CardDetailActivity.class);
                intent.putExtra("objectId",items.get(position).getObjectId());
                intent.putExtra("image",items.get(position).getImage());
                intent.putExtra("title",items.get(position).getText());
                intent.putExtra("date",items.get(position).getDate());
                intent.putExtra("detail",items.get(position).getDetail());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    class Item extends RecyclerView.ViewHolder{

        ImageView image;
        LinearLayout container;

        public Item(View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.image);
            container=(LinearLayout)itemView.findViewById(R.id.container);
        }
    }
}
