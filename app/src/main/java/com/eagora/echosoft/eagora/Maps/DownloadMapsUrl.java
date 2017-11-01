package com.eagora.echosoft.eagora.Maps;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hhaji on 26/10/17.
 */

public class DownloadMapsUrl extends AsyncTask<String, Void, JSONObject> {
    private JSONObject jsonObject;
    public JSONObject doInBackground(String... params) {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(params[0]);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            jsonObject = new JSONObject(sb.toString());

            br.close();
            iStream.close();
        } catch (IOException io) {
            Log.d("IOException", io.toString());
        }
        catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            urlConnection.disconnect();
        }
        return jsonObject;
    }
}
