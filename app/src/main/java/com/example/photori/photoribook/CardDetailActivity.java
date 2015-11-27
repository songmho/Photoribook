package com.example.photori.photoribook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit,menu);
        MenuItem editItem = menu.findItem(R.id.edit);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent=new Intent(CardDetailActivity.this,AddActivity.class);
        intent.putExtra("title",getIntent().getStringExtra("title"));
        intent.putExtra("detail",getIntent().getStringExtra("detail"));
        intent.putExtra("date",getIntent().getStringExtra("date"));
        intent.setAction("android.intent.action.edit");
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }
}
