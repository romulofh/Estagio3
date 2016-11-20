package com.example.desenvolvedor.thehelsk;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Model.Pessoa;
import Model.User;
/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{
    Intent intent;
    final String url = "https://thehelsk-romulofurtadoo548139.codeanyapp.com/login/";
    RequestQueue queue;

    Pessoa person = new Pessoa();
    User user = new User();
    Activity activity;

    Button confirm, register;

    JSONObject personJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        activity = this;

        intent = getIntent();

        queue = Volley.newRequestQueue(activity);

        confirm = (Button)findViewById(R.id.confirm_button);
        register = (Button)findViewById(R.id.register_button);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user.setEmail((((EditText)findViewById(R.id.user_email)).getText()).toString());
                user.setPassword((((EditText)findViewById(R.id.user_password)).getText()).toString());

                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    personJson = new JSONObject(response);
                                    if (!personJson.isNull("message")){
                                        Log.i("mensagem","email ou senha inválida");
                                    } else {
                                        person.setId(personJson.getInt("id"));
                                        person.setNome(personJson.getString("nome"));
                                        person.setSobrenome(personJson.getString("sobrenome"));


                                        intent = new Intent(activity, HelloActivity.class);
                                        intent.putExtra("person", person);
                                        startActivity(intent);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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
                        params.put("user[email]", user.getEmail());
                        params.put("user[password]", user.getPassword());
                        return params;
                    }
                };
                queue.add(postRequest);
                // Se estiver tudo ok, vai para a activity Status

            }
        });
        // Vai para a activity SignIn
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(activity, SignInActivity.class);
                startActivity(intent);
            }
        });
    }

}
