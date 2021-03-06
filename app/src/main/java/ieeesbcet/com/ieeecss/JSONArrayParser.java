package ieeesbcet.com.ieeecss;

/**
 * Created by sajin on 1/10/16.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONArrayParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    // constructor
    public JSONArrayParser() {

    }

    // function get json from url
    // by making HTTP POST or GET mehtod
    public JSONArray makeHttpRequest(String url, String method,
                                      List<NameValuePair> params) {

        // Making HTTP request
        try {

            // check for request method
            if(method == "POST"){
                // request method is POST
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse httpResponse = httpClient.execute(httpPost);

                if( httpResponse == null){
                    System.out.print("httpResponse error");
                }

                HttpEntity httpEntity = httpResponse.getEntity();

                if( httpEntity == null){
                    System.out.print("httpResponse error");
                }

                is = httpEntity.getContent();


                if(is ==null){
                    System.out.print("Is error");                }

            }else if(method == "GET"){
                // request method is GET
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

//        System.out.println(json);
        // try parse the string to a JSON object
//        try {
//            jObj = new JSONObject(json);
//        } catch (JSONException e) {
//            Log.e("JSON Parser", "Error parsing data " + e.toString());
//        }

        JSONArray jArray=null;
        try {
            jArray = new JSONArray(json);

//            for (int i = 0; i < jArray.length(); i++) {
//
////                System.out.println(p.get(0) + "......" + p.get(1));
//
//                JSONObject jsonobject = jArray.getJSONObject(i);
//                String named = jsonobject.getString("Name");
//                String urlk = jsonobject.getString("Email");
//
//
//                System.out.println(named + "..." + urlk);
//            }

        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("NOT WORKING HERE");
        }



        // return JSON String
        return jArray;

    }
}
