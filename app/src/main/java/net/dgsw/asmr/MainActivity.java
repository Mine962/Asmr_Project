package net.dgsw.asmr;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.dgsw.asmr.List.ListAdapter;
import net.dgsw.asmr.List.ListClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends YouTubeBaseActivity {

    ListView lists;
    ImageView vote1_, vote2_;
    private YouTubePlayerView youtubeViewer;
    YouTubePlayer.OnInitializedListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        lists = (ListView) findViewById(R.id.listview);
        ListAdapter list = new ListAdapter();
        String result = "";

        try {
            URL url = new URL("https://www.googleapis.com/youtube/v3/search?part=snippet&q=뽀모&maxResults=20&key=AIzaSyBUtESXaNNr0-TNFYkqbREg9bPbQZqjjb4");
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            while (true) {
                String data = reader.readLine();
                if (data == null) break;
                result += data;
            }
            JSONObject obj = new JSONObject(result);
            JSONArray arr = (JSONArray) obj.get("items");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject item = (JSONObject) arr.get(i);
                JSONObject channels_type = (JSONObject) item.get("id");
                JSONObject snippet = (JSONObject) item.get("snippet");
                JSONObject thumbnails = (JSONObject) snippet.get("thumbnails");
                JSONObject thumbnails_size = (JSONObject) thumbnails.get("high");
                String title = (String) snippet.get("title");
                String description = (String) snippet.get("description");
                String thumbnail = (String) thumbnails_size.get("url");
                String type = (String) channels_type.get("kind");

                if(!type.contains("channel")&&type.contains("video")){
                    String video_id = (String) channels_type.get("videoId");
                    list.addItem(thumbnail, title,  description ,video_id) ;
                }



            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        lists.setAdapter(list);

        lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                ListClass item = (ListClass) parent.getItemAtPosition(position) ;
                String video_s = item.getHide_video();
                Toast.makeText(getApplicationContext(),video_s,Toast.LENGTH_LONG).show();
            }
        }) ;




        youtubeViewer = findViewById(R.id.youtube_player);

        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                /* 초기화 성공하면 유튜브 동영상 ID를 전달하여 동영상 재생*/
                youTubePlayer.loadVideo("y_YKJ-uPIB4"); // 동영상 ID는 URL 상단의 마지막 부분이다.
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(MainActivity.this, "재생 실패", Toast.LENGTH_LONG).show();
            }
        };
        youtubeViewer.initialize("AIzaSyBUtESXaNNr0-TNFYkqbREg9bPbQZqjjb4", listener);
    }
}

