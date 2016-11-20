package com.example.desenvolvedor.thehelsk;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import Model.Event;
import Model.Pessoa;

public class EventsActivity extends ListActivity {

    Button cancel;
    Activity activity;
    Intent intent = new Intent();
    Pessoa pessoa = new Pessoa();

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        intent = getIntent();
        pessoa = (Pessoa)intent.getSerializableExtra("pessoa");

        activity = this;

        new DownloadJsonAsyncTask()
                .execute("https://thehelsk-romulofurtadoo548139.codeanyapp.com/eventos/");
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(this, EventsDetailsActivity.class);
        Event event = (Event)getListAdapter().getItem(position);

        intent.putExtra("pessoa",pessoa);
        intent.putExtra("event",event);

        startActivity(intent);
    }

    private String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
    class DownloadJsonAsyncTask extends AsyncTask<String, Void, List<Event>> {

        ProgressDialog dialog;

        @Override
        protected List<Event> doInBackground(String... params) {
            String urlString = params[0];
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(urlString);

            try {
                HttpResponse response = httpclient.execute(httpget);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream instream = entity.getContent();
                    String json = getStringFromInputStream(instream);
                    instream.close();
                    List<Event> events = getEvent(json);
                    return events;
                }
            } catch (Exception e) {
                Log.e("Erro", "Falha ao acessar Web service", e);
            }
            return null;
        }


        private List<Event> getEvent(String jsonString) {
            List<Event> events = new ArrayList<Event>();
            try {
                JSONArray eventsJson = new JSONArray(jsonString);
                JSONObject eventJson;
                for (int i = 0; i < eventsJson.length(); i++) {
                    eventJson = new JSONObject(eventsJson.getString(i));

                    Event objectEvent = new Event();
                    objectEvent.setId(Integer.parseInt(eventJson.getString("id")));
                    objectEvent.setNome(eventJson.getString("nome"));
                    objectEvent.setEstabelecimento_id(Integer.parseInt(eventJson.getString("estabelecimento_id")));
                    events.add(objectEvent);
                }
            } catch (JSONException e) {
                Log.e("Erro", "Erro no parsing do JSON", e);
            }
            return events;
        }

        @Override
        protected void onPostExecute(List<Event> result) {

            super.onPostExecute(result);
            if (result.size() > 0) {
                ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(activity, android.R.layout.simple_list_item_1, result);setListAdapter(adapter);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        activity)
                        .setTitle("Erro")
                        .setMessage("Não foi possível acessar as informações!!")
                        .setPositiveButton("OK", null);
                builder.create().show();
            }
        }

        @Override

        protected void onPreExecute() {
            super.onPreExecute();
        }

    }
}
