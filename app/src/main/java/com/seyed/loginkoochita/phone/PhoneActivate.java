package com.seyed.loginkoochita.phone;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;
import com.google.android.material.button.MaterialButton;
import com.seyed.loginkoochita.R;

import org.json.JSONException;
import org.json.JSONObject;

import ir.samanjafari.easycountdowntimer.CountDownInterface;
import ir.samanjafari.easycountdowntimer.EasyCountDownTextview;

public class PhoneActivate extends AppCompatActivity {

    private static final String TAG = "PhoneActivateActivity";
    Button btnSubmit;
    String hasUsername, status, token;
    String url = "http://185.239.106.26/api/user/activate";
    RelativeLayout layoutSnake;
    MaterialButton mBtnUnder;
    LinearLayout linearResend;
    TextView txtResend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_activate);
        txtResend = findViewById(R.id.txt_dont_send_sms);
        final PinView pinView = findViewById(R.id.picview_phone_activate);
        linearResend = findViewById(R.id.linear_resend);
        btnSubmit = findViewById(R.id.btn_submit_phone_activate);
        layoutSnake = findViewById(R.id.rel_error_phone_activate);
        mBtnUnder = findViewById(R.id.mbtnupon_phone_activate);
        EasyCountDownTextview easyCountDownTextview = findViewById(R.id.easyCountDownTextview);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    Bundle bundle = getIntent().getExtras();
                    jsonObject.put("phone", bundle.getString("phone"));
                    jsonObject.put("token", bundle.getString("token"));
                    jsonObject.put("code", pinView.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            status = response.getString("status");

                            if (status.equals("ok")) {

                                hasUsername = response.getString("hasUsername");
                                token = response.getString("token");

                                if (hasUsername.equals("false")) {
                                    Intent intent = new Intent(PhoneActivate.this, PhoneSetUserName.class);
                                    intent.putExtra("token", token);
                                    startActivity(intent);
                                }
                                if (hasUsername.equals("true")) {
                                    Toast.makeText(PhoneActivate.this, "Has UseName", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Log.d(TAG, "onResponse: ");
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

                RequestQueue requestQueue = Volley.newRequestQueue(PhoneActivate.this);
                requestQueue.add(request);

            }
        });


        txtResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearResend.setVisibility(View.VISIBLE);
                btnSubmit.setVisibility(View.INVISIBLE);
                easyCountDownTextview.setTime(0, 0, 1, 15);// setTime(days, hours, minute, second)
                Typeface typeface = ResourcesCompat.getFont(PhoneActivate.this, R.font.iransans);
                easyCountDownTextview.setTypeFace(typeface);
                easyCountDownTextview.startTimer();
                easyCountDownTextview.setOnTick(new CountDownInterface() {
                    @Override
                    public void onTick(long time) {

                    }

                    @Override
                    public void onFinish() {
                        linearResend.setVisibility(View.INVISIBLE);
                        btnSubmit.setVisibility(View.VISIBLE);
                    }
                });

            }
        });


    }
}