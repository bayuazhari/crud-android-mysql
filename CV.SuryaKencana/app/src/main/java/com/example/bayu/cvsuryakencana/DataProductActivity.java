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

public class DataProductActivity extends ActionBarActivity {
    private Button buttonTambah, buttonKembali;
    private ListView listView;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_product);

        buttonTambah = (Button) findViewById(R.id.btnAddProduct);
        buttonKembali = (Button) findViewById(R.id.btnCancel2);
        listView = (ListView)findViewById(R.id.listView);

        buttonTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iap = new Intent(DataProductActivity.this, AddProductActivity.class);
                finish();
                startActivity(iap);
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
                Intent intent = new Intent(DataProductActivity.this, UpdateProductActivity.class);
                HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
                String proId = map.get("id").toString();
                intent.putExtra("product_id",proId);
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
                loading = ProgressDialog.show(DataProductActivity.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showProduct();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest("http://10.0.3.2/tb-android/get_all_product.php");
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void showProduct(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("result");

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString("id");
                String name = jo.getString("name");

                HashMap<String,String> products = new HashMap();
                products.put("id", id);
                products.put("name", name);
                list.add(products);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                DataProductActivity.this, list, R.layout.list_item,
                new String[]{"id","name"},
                new int[]{R.id.id, R.id.name});
        listView.setAdapter(adapter);
    }
}
