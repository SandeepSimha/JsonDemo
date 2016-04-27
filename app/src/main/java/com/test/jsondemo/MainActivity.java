package com.test.jsondemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";
    String jsonStr = "{\"system\":{\"country\":\"GB\",\"sunrise\":1381107633,\"sunset\":1381149604},\"weather\":{\"id\":711,\"main\":\"Smoke\",\"description\":\"smoke\",\"icon\":\"50n\"}}";

    TestData testData = new TestData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        TextView textView = (TextView) findViewById(R.id.text);
        try {
            parseJson();
            //
            SystemData systemData = testData.getSystemData();
            Weather weather = testData.getWeather();
            //
            String systemPrintStr = String.format("country :%s, sunrise :%d, sunset%d", systemData.getCountry(),
                    systemData.getSunrise(), systemData.getSunset());
            String weatherPrintStr = String.format("id :%d, main :%s, desc: %s, icon: %s", weather.getId(),
                    weather.getMain(), weather.getDescription(), weather.getIcon());
            Log.i(TAG, "onCreate: System data ==> " + systemPrintStr);
            Log.i(TAG, "onCreate: Weather data ==> " + weatherPrintStr);
            //
            textView.setText("System:" + systemPrintStr + "\n\n\n Weather:" + weatherPrintStr);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void parseJson() throws JSONException {
        //jsonStr - this can be static , remote
        JSONObject jsonObject = new JSONObject(jsonStr);
        //
        JSONObject system = jsonObject.getJSONObject("system");
        parseSystem(system);
        //
        JSONObject weather = jsonObject.getJSONObject("weather");
        parseWeather(weather);

    }

    private void parseWeather(JSONObject weatherJsonObject) {
        Weather weather = new Weather();
        //
        int id = 0;
        try {
            id = weatherJsonObject.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String main = null;
        try {
            main = weatherJsonObject.getString("main");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String description = null;
        try {
            description = weatherJsonObject.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String icon = null;
        try {
            icon = weatherJsonObject.getString("icon");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //
        //Print the values
        String printStr = String.format("id :%d, main :%s, desc: %s, icon: %s", id, main, description, icon);
        Log.e(TAG, "parseWeather: " + printStr);
        //
        weather.setId(id);
        weather.setMain(main);
        weather.setDescription(description);
        weather.setIcon(icon);
        //
        testData.setWeather(weather);
    }

    private void parseSystem(JSONObject system) {
        //Create an system object
        SystemData systemData = new SystemData();
        //Get the values from json object
        String country = null;
        try {
            country = system.getString("country");
        } catch (JSONException e) {
            e.printStackTrace();
            country = "No country";
        }
        long sunrise = 0;
        try {
            sunrise = system.getLong("sunrise");
        } catch (JSONException e) {
            e.printStackTrace();

        }
        long sunset = 0;
        try {
            sunset = system.getLong("sunset");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Print the values
        String printStr = String.format("country :%s, sunrise :%d, sunset%d", country, sunrise, sunset);
        Log.e(TAG, "parseSystem: " + printStr);
        //Set data to model classes
        systemData.setCountry(country);
        systemData.setSunrise(sunrise);
        systemData.setSunset(sunrise);
        //Finally add to Main class.
        testData.setSystemData(systemData);
    }


}
