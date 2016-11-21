package com.example.desenvolvedor.thehelsk;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import Model.Event;
import Model.EventList;
import Model.Pessoa;

public class MyListDetailsActivity extends AppCompatActivity {

    Intent intent ;
    Activity activity;
    Pessoa pessoa;
    EventList eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list_details);

        activity = this;
        /*
        * PREENCHER OS CAMPOS DA TELA COM OS DADOS DA LISTA
        * */
        String url = "";
        new DownloadJsonAsyncTask()
                .execute(url);
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
    class DownloadJsonAsyncTask extends AsyncTask<String, Void, Event> {

        ProgressDialog dialog;

        @Override
        protected Event doInBackground(String... params) {
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
                    Event event = getEvent(json);
                    return event;
                }
            } catch (Exception e) {
                Log.e("Erro", "Falha ao acessar Web service", e);
            }
            return null;
        }


        private Event getEvent(String jsonString) throws JSONException {
            Event event = new Event();
            try {
                JSONObject eventJson = new JSONObject(jsonString);

                event.setId(eventJson.getInt("id"));
                event.setNome(eventJson.getString("nome"));
                event.setHorario(eventJson.getString("horario"));
                event.setData(eventJson.getString("data"));
            }

            catch (JSONException e) {
                Log.e("Erro", "Erro no parsing do JSON", e);
            }
            return event;
        }

        @Override
        protected void onPostExecute(Event result) {

            super.onPostExecute(result);
            if (result != null) {
                /*
                * PREENCHER OS CAMPOS DA TELA COM OS DADOS DO EVENTO
                * */


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
