package com.example.photori.photoribook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView image;
    EditText title_edit;
    EditText detail_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        image=(ImageView)findViewById(R.id.image);
        title_edit=(EditText)findViewById(R.id.title_edit);
        detail_edit=(EditText)findViewById(R.id.detail_edit);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("추억쓰기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddActivity.this, "토스트는 우유랑 먹는건데", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
