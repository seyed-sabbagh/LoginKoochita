package com.seyed.loginkoochita;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.seyed.loginkoochita.mail.MailLoginActivity;
import com.seyed.loginkoochita.phone.PhoneSignUp;

public class MainActivity extends AppCompatActivity {

    MaterialButton Phone, Mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Mail = findViewById(R.id.mail);
        Phone = findViewById(R.id.phone);

        Mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MailLoginActivity.class);
                startActivity(intent);
            }
        });
        Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, PhoneSignUp.class);
                startActivity(intent2);
            }
        });
    }
}