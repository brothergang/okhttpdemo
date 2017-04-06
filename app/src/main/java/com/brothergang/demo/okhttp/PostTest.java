package com.brothergang.demo.okhttp;

import android.util.Log;

import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by brothergang on 2017/4/5.
 */

public class PostTest {
    private static final String TAG = "PostTest";

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient mOkHttpClient = new OkHttpClient();

    private String post(String url, String json) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            return "";
        }
    }

    private String get(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            return "";
        }
    }

    private String bowlingJson(String player1, String player2) {
        return "{'winCondition':'HIGH_SCORE',"
                + "'name':'Bowling',"
                + "'round':4,"
                + "'lastSaved':1367702411696,"
                + "'dateStarted':1367702378785,"
                + "'players':["
                + "{'name':'" + player1 + "','history':[10,8,6,7,8],'color':-13388315,'total':39},"
                + "{'name':'" + player2 + "','history':[6,10,5,10,10],'color':-48060,'total':41}"
                + "]}";
    }

    public void test() {
        Runnable requestTask = new Runnable() {
            @Override
            public void run() {
                String json = bowlingJson("Jesse", "Jake");
                String response = post("http://www.roundsapp.com/post", json);
                Log.i(TAG, "post response : " + response.toString());

                try {
                    String WEATHRE_API_URL = "http://php.weather.sina.com.cn/xml.php?city=%s&password=DJOYnieT8234jlsK&day=0";
                    String urlString = String.format(WEATHRE_API_URL, URLEncoder.encode("杭州", "GBK"));
                    String getResponse = get(urlString);
                    Log.i(TAG, "get response : " + getResponse.toString());
                } catch (Exception e) {

                }
            }
        };

        Thread requestThread = new Thread(requestTask);
        requestThread.start();
    }
}
