package com.example.bayu.cvsuryakencana;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DataDistributorActivity extends ActionBarActivity {
    private Button buttonTambah, buttonKembali;
    private ListView listView;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_distributor);

        buttonTambah = (Button) findViewById(R.id.btnAddDistributor);
        buttonKembali = (Button) findViewById(R.id.btnCancel3);
        listView = (ListView)findViewById(R.id.listView2);

        buttonTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iad = new Intent(DataDistributorActivity.this, AddDistributorActivity.class);
                finish();
                startActivity(iad);
            }
        });

        buttonKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DataDistributorActivity.this, UpdateDistributorActivity.class);
                HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
                String UserId = map.get("id").toString();
                intent.putExtra("distributor_id",UserId);
                startActivity(intent);
                finish();
            }
        });
        getJSON();
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DataDistributorActivity.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showDistributor();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest("http://10.0.3.2/tb-android/get_all_distributor.php");
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void showDistributor(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("result");

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString("id");
                String name = jo.getString("name");

                HashMap<String,String> distributors = new HashMap();
                distributors.put("id", id);
                distributors.put("name", name);
                list.add(distributors);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                DataDistributorActivity.this, list, R.layout.list_item,
                new String[]{"id","name"},
                new int[]{R.id.id, R.id.name});
        listView.setAdapter(adapter);
    }
}
