package com.example.photori.photoribook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity{

    EditText name_text;
    EditText id_text;
    EditText pass_text;
    EditText pass2_text;
    Button login_button;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        name_text=(EditText)findViewById(R.id.name_text);
        id_text=(EditText)findViewById(R.id.id_text);
        pass_text=(EditText)findViewById(R.id.pass_text);
        pass2_text=(EditText)findViewById(R.id.pass2_text);
        login_button=(Button)findViewById(R.id.login_button);
        toolbar=(Toolbar)findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("회원가입");

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ddd",pass_text.getText().toString());
                Log.d("ddd",pass2_text.getText().toString());

                if(name_text.getText()==null)
                    Toast.makeText(SignupActivity.this, "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                else if(id_text.getText()==null)
                    Toast.makeText(SignupActivity.this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                else if(pass_text.getText()==null | pass2_text.getText()==null)
                    Toast.makeText(SignupActivity.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                else if(!pass_text.getText().toString().equals(pass2_text.getText().toString()))
                    Toast.makeText(SignupActivity.this, "비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();
                else {

                    ParseUser parseUser = new ParseUser();
                    parseUser.setUsername("" + id_text.getText());
                    parseUser.setEmail("" + id_text.getText());
                    parseUser.put("name", "" + name_text.getText());
                    parseUser.setPassword(pass_text.getText().toString());

                    parseUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(SignupActivity.this, "회원가입도 성공~", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                finish();
                            } else {
                                if (e.getCode() == 202)
                                    Toast.makeText(SignupActivity.this, "이미 존재하는 이메일입니다.", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getApplicationContext(), "문제가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                                //end else

                            }
                        }
                    });
                }
            }
        }

        );
    }   //onCreate
}
