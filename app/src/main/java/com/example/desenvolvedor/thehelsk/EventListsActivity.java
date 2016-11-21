package com.example.desenvolvedor.thehelsk;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.usage.UsageEvents;
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
import Model.EventList;
import Model.Pessoa;

public class EventListsActivity extends ListActivity {

    Activity activity;
    Intent intent = new Intent();
    Pessoa pessoa = new Pessoa();
    Event event = new Event();
    EventList eventList = new EventList();
    boolean myLists;
    String url;
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        intent = getIntent();
        pessoa = (Pessoa)intent.getSerializableExtra("pessoa");
        event = (Event)intent.getSerializableExtra("event");
        if (intent.getExtras().containsKey("boolean")) {
            myLists = intent.getExtras().getBoolean("boolean");
        } else {
            myLists = false;
        }

        activity = this;

        if (myLists){
            // url = "https://thehelsk-romulofurtadoo548139.codeanyapp.com/" + pessoa.getId();
        } else {
            // url = "https://thehelsk-romulofurtadoo548139.codeanyapp.com/";
        }

        new DownloadJsonAsyncTask()
                .execute(url);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        eventList = (EventList)getListAdapter().getItem(position);
        if (myLists){
            nextActivity(MyListDetailsActivity.class);
        } else {

            nextActivity(ListDetailsActivity.class);
        }
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
    class DownloadJsonAsyncTask extends AsyncTask<String, Void, List<EventList>> {

        ProgressDialog dialog;

        @Override
        protected List<EventList> doInBackground(String... params) {
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
                    List<EventList> eventLists = getEventList(json);
                    return eventLists;
                }
            } catch (Exception e) {
                Log.e("Erro", "Falha ao acessar Web service", e);
            }
            return null;
        }


        private List<EventList> getEventList(String jsonString) {
            List<EventList> eventsLists = new ArrayList<EventList>();
            try {
                JSONArray eventListsJson = new JSONArray(jsonString);
                JSONObject eventListJson;
                for (int i = 0; i < eventListsJson.length(); i++) {
                    eventListJson = new JSONObject(eventListsJson.getString(i));

                    EventList objectEventList = new EventList();
                    objectEventList.setId(Integer.parseInt(eventListJson.getString("id")));
                    objectEventList.setDescricao(eventListJson.getString("descricao"));
                    objectEventList.setNome(eventListJson.getString("nome"));
                    objectEventList.setEvent_id(event.getId());
                    eventsLists.add(objectEventList);
                }
            } catch (JSONException e) {
                Log.e("Erro", "Erro no parsing do JSON", e);
            }
            return eventsLists;
        }

        @Override
        protected void onPostExecute(List<EventList> result) {

            super.onPostExecute(result);
            if (result.size() > 0) {
                ArrayAdapter<EventList> adapter = new ArrayAdapter<EventList>(activity, android.R.layout.simple_list_item_1, result);setListAdapter(adapter);
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
    private void nextActivity(Class nextActivity){
        Intent intent = new Intent(this, nextActivity);

        intent.putExtra("pessoa",pessoa);
        intent.putExtra("event",event);
        intent.putExtra("eventList",eventList);
        startActivity(intent);
    };
}

