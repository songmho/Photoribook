package com.example.photori.photoribook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Toolbar toolbar;

    EditText id_text;
    EditText pass_text;
    Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        id_text=(EditText)findViewById(R.id.id_text);
        pass_text=(EditText)findViewById(R.id.pass_text);
        login_button=(Button)findViewById(R.id.login_button);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("로그인");


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_text.getText().toString();
                pass_text.getText().toString();
                Toast.makeText(LoginActivity.this, "로그인 성공~", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                finish();
            }
        });
    }
}
