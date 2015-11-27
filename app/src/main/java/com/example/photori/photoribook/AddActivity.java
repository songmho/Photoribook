package com.example.photori.photoribook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        getSupportActionBar().setTitle("추억 쓰기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //화살표가 생기는것


        if(getIntent().getAction()=="android.intent.action.edit"){
            title_edit.setText(getIntent().getStringExtra("title"));
            detail_edit.setText(getIntent().getStringExtra("detail"));

        }

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddActivity.this, "토스트는 우유랑 먹는건데", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add,menu);
        MenuItem addItem = menu.findItem(R.id.add);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.add){
            Toast.makeText(AddActivity.this, title_edit.getText().toString(), Toast.LENGTH_SHORT).show();
            byte [] b= new byte[10];
            Date d=new Date();
            SimpleDateFormat f=new SimpleDateFormat("yyyy.MM.dd");

            ParseObject o=new ParseObject("Memory");
            o.put("Photo",b);
            o.put("Title","d");
            o.put("Time","zz");
            o.put("Detail","dd");
            o.put("isFamous",false);
            o.saveInBackground();

           /* ParseUser p=ParseUser.getCurrentUser();
            ParseRelation<ParseObject> relation=p.getRelation("My_memory");
            relation.add(o);
            p.saveInBackground();

*/
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
