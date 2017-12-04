package com.example.jeff.test;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.jeff.test.Adapter.Custom;
import com.example.jeff.test.Helpa.HttpDataHandler;
import com.example.jeff.test.Model.Chat;
import com.example.jeff.test.Model.chickenmodel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    EditText editText;
    List<Chat> list_chat = new ArrayList<>();
    FloatingActionButton btn_send_message;
    FloatingActionButton btn_play;
    FloatingActionButton btn_pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.talk);
        mediaPlayer.setLooping(false);

        btn_play = (FloatingActionButton)findViewById(R.id.play);
        btn_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaPlayer.start();
                }
            });

        btn_pause = (FloatingActionButton)findViewById(R.id.pause);
        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
            }
        });

        listView = (ListView)findViewById(R.id.list_item);
        editText = (EditText)findViewById(R.id.user_message);
        btn_send_message = (FloatingActionButton)findViewById(R.id.fab);

        btn_send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text =  editText.getText().toString();
                Chat model = new Chat (text,true); //user send message
                list_chat.add(model);
                new SimsimiAPI().execute(list_chat);

                //remove message
                editText.setText("");
            }
        });


    }



    private class SimsimiAPI extends AsyncTask<List<Chat>,Void,String>{
        String stream = null;
        List<Chat> models;
        String text = editText.getText().toString();

        @Override
        protected String doInBackground(List<Chat>... params) {
            String url = String.format("http://sandbox.api.simsimi.com/request.p?key=%s&lc=en&ft=1.0&text=%s",getString(R.string.simsimi_api),text);
            models = params[0];
            HttpDataHandler httpDataHandler = new HttpDataHandler();
            stream = httpDataHandler.GetHTTPData(url);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            Gson gson = new Gson();
            chickenmodel response = gson.fromJson(s,chickenmodel.class);

            Chat chat = new Chat(response.getResponse(),false); //Get response from simsimi
            models.add(chat);
            Custom adapter = new Custom(models,getApplicationContext());
            listView.setAdapter(adapter);
        }
    }
}
