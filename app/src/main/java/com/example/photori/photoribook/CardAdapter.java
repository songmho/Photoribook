package com.example.photori.photoribook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class CardAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<CardItem> items;


    final int HOLDER=0;
    final int FOOTER=1;

    public CardAdapter(Context applicationContext, ArrayList<CardItem> items) {
        this.context=context;
        this.items=items;
    }

    @Override
    public int getItemViewType(int position) {
        if(position<items.size())
            return HOLDER;
        else if(position==items.size())
            return FOOTER;
        else
            return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==HOLDER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
            return new Item(v);
        }
        else{
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fab_footer, parent, false);
            return new Item(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position < items.size()) {
            ((Item) holder).image.setBackgroundColor(0xff123456);
            ((Item) holder).select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            ((Item) holder).date.setText(items.get(position).getDate());
            ((Item) holder).text.setText(items.get(position).getText());
        }
        else if(position==items.size()){

        }
    }

    @Override
    public int getItemCount() {
        return items.size()+1;
    }
}

class Item extends RecyclerView.ViewHolder{

    ImageView image;
    Button select;
    TextView date;
    TextView text;

    public Item(View itemView) {
        super(itemView);
        image=(ImageView)itemView.findViewById(R.id.image);
        select=(Button)itemView.findViewById(R.id.selct_button);
        date=(TextView)itemView.findViewById(R.id.text_time);
        text=(TextView)itemView.findViewById(R.id.text_text);

    }
}

class Footer extends RecyclerView.ViewHolder{

    public Footer(View itemView) {
        super(itemView);
    }
}