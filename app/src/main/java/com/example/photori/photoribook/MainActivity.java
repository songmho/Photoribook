package com.example.photori.photoribook;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences shpref=getSharedPreferences("myPref",0);
        //앱 종류 여부에 상관하지 않고 값을 저장하고 싶을때
       /* int count=shpref.getInt("Count",-100);
        if(count==-100){
            startActivity(new Intent(MainActivity.this, SplashActivity.class));
            count=1;
        }
        else {
            count++;
        }SharedPreferences.Editor editor=shpref.edit();
        editor.putInt("Count",count);
        editor.commit();
*/
        setContentView(R.layout.activity_main);


    }
}
