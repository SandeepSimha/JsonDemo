package com.test.jsondemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";
    String jsonStr = "{\"system\":{\"country\":\"GB\",\"sunrise\":1381107633,\"sunset\":1381149604},\"weather\":{\"id\":711,\"main\":\"Smoke\",\"description\":\"smoke\",\"icon\":\"50n\"}}";

    TestData testData = new TestData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
//        parseStaticData();
        //Load data from url
        loadDataFromUrl();
    }

    private void parseStaticData() {
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

    public void loadDataFromUrl() {
        final String url = "http://api.androidhive.info/contacts/";

        //OnCreate ==>UI
        //It will throw exception when perform n/w operation
//        NetworkOnMainThreadException
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                return Util.performGET(params[0]);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                Toast.makeText(MainActivity.this, "Result is :" + s, Toast.LENGTH_LONG).show();
                parseContacts(s);
            }
        }.execute(url);
    }

    public void parseContacts(String jsonStr) {
        //
        ContactsResponse response = new Gson().fromJson(jsonStr, ContactsResponse.class);
        final List<Contacts> contactsList = response.getContacts();
        int size = contactsList.size();
        Log.e("MainActivity", "parseContacts: no of contacts is -" + size);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(createSimpleAdapter(contactsList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contacts contacts = contactsList.get(position);
                Log.e("MainActivity", "onItemClick: name - " + contacts.getName());
            }
        });
    }

    public ListAdapter createArrayAdapter(List<Contacts> contactsList) {
        ArrayAdapter<Contacts> adapter = new ArrayAdapter<Contacts>(this,
                android.R.layout.simple_list_item_1, contactsList);
        return adapter;
    }

    public ListAdapter createSimpleAdapter(List<Contacts> contactsList) {
        List<Map<String, String>> mapsList = new ArrayList<>();

        for (Contacts contacts : contactsList) {
            //Creating Map
            Map<String, String> map = new HashMap<>();
            map.put("name", contacts.getName());
            map.put("email", contacts.getEmail());
            map.put("address", contacts.getAddress());
            //Insert
            mapsList.add(map);
        }
        String[] from = new String[]{"name", "email", "address"};
        int to[] = new int[]{R.id.txt_name, R.id.txt_email, R.id.txt_address};

        SimpleAdapter adapter = new SimpleAdapter(this, mapsList, R.layout.contacts_row,
                from, to);

        return adapter;
    }


}
