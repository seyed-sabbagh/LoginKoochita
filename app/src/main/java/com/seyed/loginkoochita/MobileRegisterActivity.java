package com.seyed.loginkoochita;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;

public class MobileRegisterActivity extends AppCompatActivity {

    private static final String TAG = "tes";
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_register);

        final PinView pinView = findViewById(R.id.firstPinView);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onCreate: " + pinView.getText());
            }
        });


    }
}