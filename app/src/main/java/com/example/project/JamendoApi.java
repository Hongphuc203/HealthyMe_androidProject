package com.example.project;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JamendoApi {
    public interface Callback {
        void onSuccess(List<Track> tracks);
        void onFailure(Exception e);
    }

    public static void fetchTracks(Callback callback) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.jamendo.com/v3.0/tracks/?client_id=ef6431fd&format=json&limit=50&order=popularity_total";
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String json = response.body().string();
                    List<Track> tracks = parseTracks(json);

                    new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(tracks));
                } catch (Exception e) {
                    callback.onFailure(e);
                }
            }
        });
    }

    private static List<Track> parseTracks(String json) {
        List<Track> list = new ArrayList<>();
        try {
            JSONArray arr = new JSONObject(json).getJSONArray("results");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Track t = new Track();
                t.setName(obj.getString("name"));
                t.setArtist(obj.getString("artist_name"));
                t.setAudioUrl(obj.getString("audio"));
                t.setAlbumImage(obj.getString("album_image"));
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
