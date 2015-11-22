package com.example.photori.photoribook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.Parse;
import com.parse.ParseUser;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by songmho on 15. 11. 20..
 */
public class MypageActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView profile;
    TextView name;
    TextView email;
    TextView today;
    TextView total;


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

    }
}
