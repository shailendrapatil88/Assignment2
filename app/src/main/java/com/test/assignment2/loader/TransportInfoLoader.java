package com.test.assignment2.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.test.assignment2.manager.TransportManager;
import com.test.assignment2.pojo.TransportInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shailendra on 3/8/2016.
 */
public class TransportInfoLoader extends AsyncTaskLoader<ArrayList<TransportInfo>> {


    private static final String URL = "http://express-it.optusnet.com.au/sample.json";

    public TransportInfoLoader(Context context) {
        super(context);
    }

    @Override
    public ArrayList<TransportInfo> loadInBackground() {
        String response = new TransportManager().getAllInformationFromServer(URL);
        if (response != null && !response.equalsIgnoreCase("")) {
            return parseInformation(response);
        }
        return null;
    }

    private ArrayList<TransportInfo> parseInformation(String response) {
        ArrayList<TransportInfo> transportInfos = new ArrayList<>();
        try {

            JSONArray responseArray = new JSONArray(response);
            if (responseArray.length() > 0) {
                for (int i = 0; i < responseArray.length(); i++) {
                    JSONObject jsonObject = responseArray.optJSONObject(i);
                    TransportInfo transportInfo = new TransportInfo();
                    transportInfo.setId(jsonObject.optString("id"));
                    transportInfo.setName(jsonObject.optString("name"));
                    JSONObject modeObject = jsonObject.optJSONObject("fromcentral");
                    transportInfo.setFromCentral(new TransportInfo.Mode(modeObject.optString("car"),modeObject.optString("train")));
                    JSONObject locationObject = jsonObject.optJSONObject("location");
                    transportInfo.setLocation(new TransportInfo.TransportLocation(locationObject.optDouble("latitude"), locationObject.optDouble("longitude")));
                    transportInfos.add(transportInfo);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return transportInfos;
    }
}
