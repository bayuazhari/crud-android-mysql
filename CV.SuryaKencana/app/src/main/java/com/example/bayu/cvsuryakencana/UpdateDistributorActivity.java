package com.example.bayu.cvsuryakencana;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UpdateDistributorActivity extends ActionBarActivity {
    private EditText editTextId, editTextNama, editTextAlamat, editTextKota, editTextTelp;
    private Button btnUpdate, btnHapus, btnKembali;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_distributor);

        Intent intent = getIntent();
        id = intent.getStringExtra("distributor_id");

        editTextId = (EditText) findViewById(R.id.etIdD);
        editTextNama = (EditText) findViewById(R.id.etNameD2);
        editTextAlamat = (EditText) findViewById(R.id.etAddressD2);
        editTextKota = (EditText) findViewById(R.id.etCityD2);
        editTextTelp = (EditText) findViewById(R.id.etTelpD2);
        btnUpdate = (Button) findViewById(R.id.btnUpdateD);
        btnHapus = (Button) findViewById(R.id.btnDeleteD);
        btnKembali = (Button) findViewById(R.id.btnCancelD2);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextNama.getText().toString().trim().length() == 0
                        || editTextAlamat.getText().toString().trim().length() == 0
                        || editTextKota.getText().toString().trim().length() == 0
                        || editTextTelp.getText().toString().trim().length() == 0) {
                    Toast.makeText(UpdateDistributorActivity.this, "Mohon datanya dilengkapi terlebih dahulu!", Toast.LENGTH_LONG).show();
                } else {
                    updateDistributor();
                    startActivity(new Intent(UpdateDistributorActivity.this, DataDistributorActivity.class));
                    finish();
                }
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteDistributor();
            }
        });

        editTextId.setText(id);
        getDistibutor();
    }

    private void getDistibutor(){
        class GetDistributor extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateDistributorActivity.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showDistributor(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam("http://10.0.3.2/tb-android/get_distributor.php?id=",id);
                return s;
            }
        }
        GetDistributor gd = new GetDistributor();
        gd.execute();
    }

    private void showDistributor(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject c = result.getJSONObject(0);
            String name = c.getString("name");
            String address = c.getString("address");
            String city = c.getString("city");
            String telp = c.getString("telp");

            editTextNama.setText(name);
            editTextAlamat.setText(address);
            editTextKota.setText(city);
            editTextTelp.setText(telp);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateDistributor(){
        final String name = editTextNama.getText().toString();
        final String address = editTextAlamat.getText().toString();
        final String city = editTextKota.getText().toString();
        final String telp = editTextTelp.getText().toString();

        class UpdateDistributor extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateDistributorActivity.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(UpdateDistributorActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap();
                hashMap.put("id",id);
                hashMap.put("name",name);
                hashMap.put("address",address);
                hashMap.put("city",city);
                hashMap.put("telp",telp);


                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest("http://10.0.3.2/tb-android/update_distributor.php", hashMap);

                return s;
            }
        }

        UpdateDistributor ud = new UpdateDistributor();
        ud.execute();
    }

    private void deleteDistributor(){
        class DeleteDistributor extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateDistributorActivity.this, "Deleting...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(UpdateDistributorActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam("http://10.0.3.2/tb-android/delete_distributor.php?id=", id);
                return s;
            }
        }

        DeleteDistributor dd = new DeleteDistributor();
        dd.execute();
    }

    private void confirmDeleteDistributor(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah anda ingin mengapus data ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteDistributor();
                        startActivity(new Intent(UpdateDistributorActivity.this, DataDistributorActivity.class));
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
