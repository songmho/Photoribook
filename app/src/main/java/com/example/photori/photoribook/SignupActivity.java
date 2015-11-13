package com.example.photori.photoribook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity{

    EditText id_text;
    EditText pass_text;
    EditText pass2_text;
    Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        id_text=(EditText)findViewById(R.id.id_text);
        pass_text=(EditText)findViewById(R.id.pass_text);
        pass2_text=(EditText)findViewById(R.id.pass2_text);
        login_button=(Button)findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_text.getText().toString();
                pass_text.getText().toString();
                pass2_text.getText().toString();
                Toast.makeText(SignupActivity.this, "회원가입도 성공~", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignupActivity.this, MainActivity.class));
                finish();
            }
        }

        );
    }
}
