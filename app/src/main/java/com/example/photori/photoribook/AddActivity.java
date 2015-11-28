package com.example.photori.photoribook;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    Toolbar toolbar;
    ImageView image;
    EditText title_edit;
    EditText detail_edit;

    Bitmap bitmap;

    private String picturePath = "";

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
            Glide.with(this).load(getIntent().getByteArrayExtra("image"))
                    .placeholder(R.drawable.edit_default).into(image);
            bitmap=byteArrayToBitmap(getIntent().getByteArrayExtra("image"));
        }

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
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
            if(getIntent().getAction()=="android.intent.action.edit"){
                final ParseUser p=ParseUser.getCurrentUser();
                final ParseRelation<ParseObject> relation=p.getRelation("My_memory");
                relation.getQuery().getInBackground(getIntent().getStringExtra("objectId"), new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject o, ParseException e) {
                        if(o==null)
                            Log.d("ddd","fffffff");
                        ParseFile photo=new ParseFile("photo.jpg",bitmapTobyte(bitmap));
                        o.put("Photo",photo);
                        o.put("Title",title_edit.getText().toString());
                        o.put("Time",getIntent().getStringExtra("date"));
                        o.put("Detail",detail_edit.getText().toString());
                        o.put("isFamous",false);
                        o.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                            }
                        });
                    }
                });

                finish();
                return true;
            }
            else {
                Date d = new Date();
                SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd");
                final ParseUser p = ParseUser.getCurrentUser();
                final ParseRelation<ParseObject> relation = p.getRelation("My_memory");

                final ParseObject o = new ParseObject("Memory");
                ParseFile photo = new ParseFile("photo.jpg", bitmapTobyte(bitmap));
                o.put("Photo", photo);
                o.put("Title", title_edit.getText().toString());
                o.put("Time", f.format(d));
                o.put("Detail", detail_edit.getText().toString());
                o.put("isFamous", false);
                o.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        relation.add(o);
                        p.saveInBackground();
                    }
                });
                //  o.saveInBackground();
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data){
            Uri imageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(imageUri, filePathColumn,
                    null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView=(ImageView)findViewById(R.id.image);
            bitmap=BitmapFactory.decodeFile(picturePath);
            imageView.setImageBitmap(bitmap);
        }
    }

    private byte[] bitmapTobyte(Bitmap bm) {
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] bytes=stream.toByteArray();
        return bytes;
    }

    public Bitmap byteArrayToBitmap( byte[] $byteArray ) {
        Bitmap bitmap = BitmapFactory.decodeByteArray( $byteArray, 0, $byteArray.length ) ;
        return bitmap ;
    }

}
