package com.adsmedia.mastermodul;


import static com.adsmedia.mastermodul.Connections.KEY_EMP_ADS;
import static com.adsmedia.mastermodul.Connections.KEY_EMP_PACK;
import static com.adsmedia.mastermodul.Connections.KEY_EMP_STATUS;
import static com.adsmedia.mastermodul.Connections.TAG_ADS;
import static com.adsmedia.mastermodul.Connections.TAG_ID;
import static com.adsmedia.mastermodul.Connections.TAG_JSON_ARRAY;
import static com.adsmedia.mastermodul.Connections.TAG_PACK;
import static com.adsmedia.mastermodul.Connections.URL_ADD;
import static com.adsmedia.mastermodul.Connections.URL_GET_ALL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import com.startapp.sdk.adsbase.StartAppSDK;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class RestApi {
    private static final String ALGORITHM = "AES";
    private static final byte[] SALT = "tHeApAcHe6410111".getBytes();// THE KEY MUST BE SAME
    static Key getSalt() {
        return new SecretKeySpec(SALT, ALGORITHM);
    }
    public static String keyLogic = "f322slsBbkq5SzO3YSbU+msb6fl4Gt02X3YeJ3W+JfkiLoQOSAOHw9qDSlRrCK8Zdk3f9U34JRCZOyiUo2gTYg==bZqG0G0OftfZHhOwwGHVnA==";
    public static String LoadData(String value) {
        if (value == null) {
            return null;
        }

        Key salt = getSalt();
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, salt);
            byte[] decodedValue = Base64.decode(value, Base64.DEFAULT);
            byte[] decValue = cipher.doFinal(decodedValue);
            return new String(decValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String SetData(String value) {
        try {
            String result = value.replace("dk3f9U34JRCZOyiUo2gTYg=="," ");
            String arr[] = result.split(" ", 2);
            String CODEGITH = arr[0];
            return new String(CODEGITH);
        } catch (Exception e) {
            e.printStackTrace();
        }
       return null;
    }

    public static String JSON_STRING;
    public static void getJSON(Activity activity, String packName){
        class GetJSON extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                showData(activity, packName);

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(RestApi.LoadData(RestApi.SetData(URL_GET_ALL)));
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
    public static String NAMEPN;
    public static String ADS;
    private static void showData(Activity activity, String packName){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                if (jo.getString(TAG_PACK).equals(packName)) {
                    String id = jo.getString(TAG_ID);
                    String name = jo.getString(TAG_PACK);
                    String ads = jo.getString(TAG_ADS);

                    HashMap<String,String> employees = new HashMap<>();
                    employees.put(TAG_ID,id);
                    employees.put(TAG_PACK,name);
                    employees.put(TAG_ADS,ads);
                    list.add(employees);
                    ADS = ads;
                    NAMEPN = name;
                }
            }
            StartAppSDK.init(activity, ADS, false);
            if (packName.equals(NAMEPN)){
                //Toast.makeText(activity, "PN Sudah Terdaftar " +NAMEPN +"App ID "+ ADS , Toast.LENGTH_SHORT).show();
            } else {
                addData(packName, activity);
            }
        } catch (JSONException e) {
            //Toast.makeText(activity, "Error" + TAG_ADS , Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }
    public static void addData(String packegname, Activity activity){
        class addData extends AsyncTask<Void,Void,String>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(KEY_EMP_PACK,packegname);
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(RestApi.LoadData(RestApi.SetData(URL_ADD)), params);
                return res;
            }
        }

        addData ae = new addData();
        ae.execute();
    }


}
