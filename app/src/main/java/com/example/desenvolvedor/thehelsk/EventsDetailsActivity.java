package com.example.desenvolvedor.thehelsk;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        TextView eventName = (TextView)findViewById(R.id.text_event_name);
        eventName.setText(event.getNome());

        Button confirmButton = (Button)findViewById(R.id.button_confirm);
        Button cancelButton = (Button)findViewById(R.id.cancel_button);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextView(EventListsActivity.class);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextView(EventsActivity.class);
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
