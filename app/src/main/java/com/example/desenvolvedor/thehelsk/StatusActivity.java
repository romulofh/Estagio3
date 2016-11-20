package com.example.desenvolvedor.thehelsk;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import Model.Pessoa;

public class StatusActivity extends AppCompatActivity {

    Intent intent;
    Pessoa pessoa;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        activity = this;

        intent = getIntent();
        pessoa = (Pessoa)intent.getSerializableExtra("pessoa");

        TextView my_name = (TextView)findViewById(R.id.my_name);

        if (my_name != null) {
            my_name.setText(pessoa.getNome() + pessoa.getSobrenome());
        }

        Button events = (Button)findViewById(R.id.button_events);
        Button  myEvents = (Button)findViewById(R.id.button_my_events);

        intent.putExtra("pessoa", pessoa);
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(activity, EventsActivity.class);
                startActivity(intent);
            }
        });

        myEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(activity, EventsActivity.class);
                startActivity(intent);
            }
        });
    }
}
