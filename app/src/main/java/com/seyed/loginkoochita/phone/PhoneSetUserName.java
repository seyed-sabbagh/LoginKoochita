package com.seyed.loginkoochita.phone;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.seyed.loginkoochita.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PhoneSetUserName extends AppCompatActivity {

    private static final String TAG = "PhoneSetUserName";
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_set_user_name);

        editText = findViewById(R.id.edt_input_usename_mobile);


        Bundle bundle = getIntent().getExtras();
        String token = bundle.getString("token");
        Log.d(TAG, "onCreate: " + token);
        button = findViewById(R.id.btn_submit_phone_username);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = editText.getText().toString();

                JSONObject jsonObject = new JSONObject();
                try {
//                    jsonObject.put("Authorization", token);
                    jsonObject.put("username", userName);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, "http://185.239.106.26/api/user/setUsername",
                        jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: " + response);
                        editText.setText("");
                        try {
                            String status = response.getString("status");
                            if (status.equals("ok")) {
                                Toast.makeText(PhoneSetUserName.this, "OK", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onResponse: " + error.toString());
                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Authorization", "Bearer " + token);
                        return headers;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(PhoneSetUserName.this);
                requestQueue.add(req);

            }
        });


    }
}