package com.example.photori.photoribook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by songmho on 15. 11. 20..
 */
public class CardDetailActivity extends AppCompatActivity {


    ImageView image;
    TextView detail;
    TextView date;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);

        Intent intent=getIntent();

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        image=(ImageView)findViewById(R.id.image);
        detail=(TextView)findViewById(R.id.text_detail);
        date=(TextView)findViewById(R.id.text_date);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(intent.getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        detail.setText(intent.getStringExtra("detail"));
        date.setText(intent.getStringExtra("date"));

    }
}
