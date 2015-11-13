package com.example.photori.photoribook;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerlayout;
    NavigationView navigationView;

    Toolbar toolbar;

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

        drawerlayout=(DrawerLayout)findViewById(R.id.drawerlayout);
        navigationView=(NavigationView)findViewById(R.id.navigationView);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                return clickDrawerMenu(menuItem);
            }
        });

        toolbar.setNavigationIcon(R.drawable.drawericon);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerlayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private boolean clickDrawerMenu(MenuItem menuItem) {
        return false;
    }
}
