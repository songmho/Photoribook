package com.example.photori.photoribook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;


public class CardAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<CardItem> items;


    final int HOLDER=0;
    final int FOOTER=1;

    int cur_layout;

    public CardAdapter(Context applicationContext, ArrayList<CardItem> items, int layout) {
        this.context=applicationContext;
        this.items=items;
        this.cur_layout=layout;
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
        else if(viewType==FOOTER){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fab_footer, parent, false);
            return new Item(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(position < items.size()) {
            Glide.with(context).load(items.get(position).getImage())
                    .placeholder(R.drawable.edit_default).into(((Item)holder).image);

            ((Item) holder).select.setSelected(items.get(position).isSelect());

            ((Item) holder).select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(items.get(position).isSelect()){
                        ((Item)holder).select.setSelected(false);
                        items.get(position).setSelect(false);

                        final ParseUser p=ParseUser.getCurrentUser();
                        final ParseRelation<ParseObject> relation=p.getRelation("My_memory");
                        relation.getQuery().getInBackground(items.get(position).getObjectId(), new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject o, ParseException e) {
                                o.put("isFamous",false);
                                o.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                    }
                                });
                            }
                        });
                    }
                    else{
                        ((Item)holder).select.setSelected(true);
                        items.get(position).setSelect(true);
                        final ParseUser p=ParseUser.getCurrentUser();
                        final ParseRelation<ParseObject> relation=p.getRelation("My_memory");
                        relation.getQuery().getInBackground(items.get(position).getObjectId(), new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject o, ParseException e) {
                                o.put("isFamous",true);
                                o.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                    }
                                });
                            }
                        });
                    }

                }
            });

            ((Item) holder).date.setText(items.get(position).getDate());
            ((Item) holder).text.setText(items.get(position).getText());
            ((Item)holder).cardView.setOnClickListener(new View.OnClickListener() {
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
        else if(position==items.size() && cur_layout==R.layout.activity_main){

        }
    }

    @Override
    public int getItemCount() {
        if(cur_layout==R.layout.activity_main)
        return items.size()+1;
        else
            return items.size();
    }
    class Item extends RecyclerView.ViewHolder{

        ImageView image;
        Button select;
        TextView date;
        TextView text;
        CardView cardView;

        public Item(View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.image);
            select=(Button)itemView.findViewById(R.id.select_button);
            date=(TextView)itemView.findViewById(R.id.text_time);
            text=(TextView)itemView.findViewById(R.id.text_text);
            cardView=(CardView)itemView.findViewById(R.id.cardView);
        }
    }

    class Footer extends RecyclerView.ViewHolder{

        public Footer(View itemView) {
            super(itemView);
        }
    }
}

