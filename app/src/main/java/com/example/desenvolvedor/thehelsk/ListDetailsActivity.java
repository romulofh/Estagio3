package com.example.desenvolvedor.thehelsk;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

import Model.Event;
import Model.EventList;
import Model.Pessoa;

public class ListDetailsActivity extends AppCompatActivity {

    Activity activity;
    Intent intent;
    Event event;
    EventList eventList;
    Pessoa pessoa;
    String url;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_details);

        activity = this;
        intent = getIntent();
        pessoa = (Pessoa) intent.getSerializableExtra("pessoa");
        event = (Event) intent.getSerializableExtra("event");
        eventList = (EventList) intent.getSerializableExtra("eventList");
        url = "https://thehelsk-romulofurtadoo548139.codeanyapp.com/lists/event/" + event.getId(); //????
        queue = Volley.newRequestQueue(activity);
        Button cancel = (Button) findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextView(EventListsActivity.class);
            }
        });

        Button confirm = (Button) findViewById(R.id.button_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

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
                        params.put("listapessoas[pessoa_id]", String.valueOf(pessoa.getId()));
                        params.put("listapessoas[list_id]", String.valueOf(eventList.getId()));
                        return params;
                    }
                };
                queue.add(postRequest);
                nextView(StatusActivity.class);
            }
        });
    }

    private void nextView(Class nextActivity){
        intent = new Intent(activity, nextActivity);
        intent.putExtra("pessoa",pessoa);
        intent.putExtra("event", event);
        startActivity(intent);
    }
}
