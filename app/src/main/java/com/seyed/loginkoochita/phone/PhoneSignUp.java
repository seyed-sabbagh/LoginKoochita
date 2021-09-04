package com.seyed.loginkoochita.phone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.seyed.loginkoochita.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PhoneSignUp extends AppCompatActivity {
    private static final String TAG = "PhoneSignUpActivity";
    Button btnSubmit;
    EditText edt_input_phone_number;
    MaterialButton mBtnUnder;
    RelativeLayout layoutSnake;
    String PhoneNumber;
    String url = "http://185.239.106.26/api/user/signUp";
    String status, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_sing_up);

        layoutSnake = findViewById(R.id.rel_error);
        edt_input_phone_number = findViewById(R.id.edt_input_phone);
        btnSubmit = findViewById(R.id.btn_submit_phone_login);
        mBtnUnder = findViewById(R.id.mbtnupon_phone_login);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneNumber = edt_input_phone_number.getText().toString();


                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("phone", PhoneNumber);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            status = response.getString("status");
                            Log.d(TAG, "onResponse: " + status);
                            if (status.equals("ok")) {
                                token = response.getString("token");
                                Intent intent = new Intent(PhoneSignUp.this, PhoneActivate.class);
                                intent.putExtra("token", token);
                                intent.putExtra("phone", PhoneNumber);
                                startActivity(intent);
                            } else {
                                layoutSnake.setVisibility(View.VISIBLE);
                                TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
                                translateAnimation.setDuration(3000);
                                translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
                                translateAnimation.setFillAfter(false);
                                mBtnUnder.setAnimation(translateAnimation);
                                translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        layoutSnake.setVisibility(View.INVISIBLE);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onResponse: " + error);

                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(PhoneSignUp.this);
                requestQueue.add(request);

            }
        });


    }

}