package com.adsmedia.mastermodul;

import static com.adsmedia.mastermodul.RestApi.getJSON;
import static com.adsmedia.mastermodul.RestApi.keyLogic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.startapp.sdk.adsbase.StartAppSDK;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class Config {
    public static void loadKeys(Activity activity, String packName){
        getJSON(activity, packName);
    }
}
