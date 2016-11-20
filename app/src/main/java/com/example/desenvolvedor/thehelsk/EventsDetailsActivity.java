package com.example.desenvolvedor.thehelsk;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import Model.Event;
import Model.Pessoa;

public class EventsDetailsActivity extends AppCompatActivity {

    Activity activity;
    Intent intent;
    Pessoa pessoa;
    Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_details);

        activity = this;
        intent = getIntent();
        pessoa = (Pessoa)intent.getSerializableExtra("pessoa");
        event = (Event)intent.getSerializableExtra("event");

        final String url = "https://thehelsk-romulofurtadoo548139.codeanyapp.com/eventos/";

    }
}
