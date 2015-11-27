package com.example.photori.photoribook;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MypageActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView profile;
    TextView name;
    TextView email;
    TextView today;
    TextView total;

    ParseFile profile_parse;
    int CAMERA_REQUEST=1000;
    int SELECT_FILE=2000;
    CharSequence[] item={"카메라","갤러리에서 사진 가져오기","삭제"};
    File file_up_path=new File("data/data/com.example.photori.photoribook/files/");
    ParseUser user=ParseUser.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("마이페이지");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profile=(ImageView)findViewById(R.id.profile);
        name=(TextView)findViewById(R.id.name);
        email=(TextView)findViewById(R.id.email);
        today=(TextView)findViewById(R.id.today);
        total=(TextView)findViewById(R.id.total);

        name.setText(ParseUser.getCurrentUser().getString("name"));
        email.setText(ParseUser.getCurrentUser().getEmail());

        Glide.with(getApplicationContext()).load(R.drawable.ss).
                bitmapTransform(new CropCircleTransformation(getApplicationContext())).into(profile);


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakingAlertDialog();
            }
        });
    }


    private void MakingAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MypageActivity.this, R.style.dialog);
        builder.setTitle("프로필 사진 추가하기");
        builder.setItems(item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                if (item[position].equals("카메라")) {
                    Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (camera.resolveActivity(getPackageManager()) != null)
                        startActivityForResult(camera, CAMERA_REQUEST);
                } else if (item[position].equals("갤러리에서 사진 가져오기")) {
                    Intent gallery = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    gallery.addCategory(Intent.CATEGORY_OPENABLE);
                    gallery.setType("image/*");
                    startActivityForResult(Intent.createChooser(gallery, "갤러리 선택"), SELECT_FILE);
                } else if (item[position].equals("삭제")) {
                    File[] files = file_up_path.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        String fname = files[i].getName();
                        if (fname.equals("profile.jpg"))
                            files[i].delete();
                    }
                    ParseUser.getCurrentUser().remove("profile");
                    Toast.makeText(getApplicationContext(), "삭제하였습니다.", Toast.LENGTH_SHORT).show();
                    Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.photoribook_logo);
                    profile.setImageBitmap(b);
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap thum = null;
        if(resultCode==RESULT_OK && data!=null){
            if(requestCode==CAMERA_REQUEST){
                thum=(Bitmap)data.getExtras().get("data");
                profile.setImageBitmap(thum);
                imgSendParse(thum);
            }
            else if(requestCode==SELECT_FILE){
                Uri uri=data.getData();
                try {
                    thum = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    profile.setImageBitmap(thum);
                    imgSendParse(thum);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            File file=new File("profile.jpg");
            FileOutputStream fos= null;
            try {
                fos = openFileOutput("profile.jpg",0);
                thum.compress(Bitmap.CompressFormat.JPEG,50,fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void imgSendParse(Bitmap thum) {
        profile_parse=new ParseFile("profile.jpg",bitmapTobyte(thum));
        if(user.get("profile")!=null)
            user.remove("profile");
        user.put("profile", profile_parse);
        user.saveInBackground();
    }

    private byte[] bitmapTobyte(Bitmap bm) {
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] bytes=stream.toByteArray();
        return bytes;
    }
}
