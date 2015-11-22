package com.example.photori.photoribook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {


    EditText id_text;
    EditText pass_text;
    Button login_button;
    Button signup_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id_text=(EditText)findViewById(R.id.id_text);
        pass_text=(EditText)findViewById(R.id.pass_text);
        login_button=(Button)findViewById(R.id.login_button);
        signup_button=(Button)findViewById(R.id.signup_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       //login버튼 클릭 시

                new ParseUser().logInInBackground(id_text.getText().toString(), pass_text.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if (parseUser != null) {
                            Toast.makeText(LoginActivity.this, "로그인 성공~", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "아이디와 비밀번호를 다시 확인 하십시오.", Toast.LENGTH_SHORT).show();
                            //   startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        }
                    }
                });
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {       //signup버튼 클릭 시
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                finish();
            }
        });
    }   //onCreate
}
