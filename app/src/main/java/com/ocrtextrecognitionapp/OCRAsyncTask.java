package com.ocrtextrecognitionapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by suhasbachewar on 10/5/16.
 */
public class OCRAsyncTask extends AsyncTask {

    public OCRAsyncTask(MainActivity mainActivity) {

    }

    @Override
    protected String doInBackground(Object[] params) {

        try {
            String str = sendPost();
            return str;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String sendPost() throws Exception {

        String url = "https://api.ocr.space/parse/image";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");


        JSONObject postDataParams = new JSONObject();
        postDataParams.put("apikey", "10f838f49a88957");//TODO Add your Registered API key
        postDataParams.put("isOverlayRequired", "false");
        postDataParams.put("url", "http://blog.cubeacon.com/wp-content/uploads/2015/05/Cubeacon_for_logistic_container.jpg");
        postDataParams.put("language", "eng");


        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(getPostDataString(postDataParams));
        wr.flush();
        wr.close();

//        int responseCode = con.getResponseCode();
//        System.out.println("\nSending 'POST' request to URL : " + url);
//        System.out.println("Post parameters : " + postDataParams.toString());
//        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
//        System.out.println("Response: " + response.toString());
        return String.valueOf(response);
    }

    @Override
    protected void onPostExecute(Object o) {
        String response = (String) o;
        super.onPostExecute(o);
        System.out.println("Response: " + response.toString());

        try {
            JSONObject mainObj = new JSONObject(response);
            JSONArray jsonArray = mainObj.getJSONArray("ParsedResults");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                System.out.println("jsonObject: " + jsonObject.toString());
                String name = jsonObject.optString("ParsedText");
                System.out.println("name"+name);
                break;
            }


        } catch (JSONException e) {

            e.printStackTrace();
        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
}


