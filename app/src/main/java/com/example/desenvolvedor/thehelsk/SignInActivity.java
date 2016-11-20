package com.example.desenvolvedor.thehelsk;

/**
 * Created by Desenvolvedor on 20/11/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import Model.Pessoa;
import Model.User;

public class SignInActivity extends AppCompatActivity {

    Intent intent;
    final String url = "https://thehelsk-romulofurtadoo548139.codeanyapp.com/pessoas";
    RequestQueue queue;

    Pessoa person = new Pessoa();
    User user = new User();
    Activity activity;

    Boolean nextActivity = false;

    Button confirm, cancel;

    RadioGroup radioSexGroup;
    RadioButton radioSexButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        activity = this;

        intent = getIntent();

        queue = Volley.newRequestQueue(activity);

        confirm = (Button)findViewById(R.id.confirm_button);
        cancel = (Button)findViewById(R.id.cancel_button);



        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                person.setNome((((EditText)findViewById(R.id.person_name)).getText()).toString());
                person.setSobrenome((((EditText)findViewById(R.id.person_surname)).getText()).toString());




                user.setEmail((((EditText)findViewById(R.id.user_email)).getText()).toString());
                user.setPassword((((EditText)findViewById(R.id.user_password)).getText()).toString());

                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // response
                                nextActivity = true;
                                Log.d("Response", response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Log.d("Response", error.getMessage());
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("pessoa[nome]", person.getNome());
                        params.put("pessoa[sobrenome]", person.getSobrenome());
                        params.put("pessoa[user_attributes[email]]", user.getEmail());
                        params.put("pessoa[user_attributes[password]]",user.getPassword());
                        return params;
                    }
                };
                queue.add(postRequest);

                intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
            }
        });
    }



}
