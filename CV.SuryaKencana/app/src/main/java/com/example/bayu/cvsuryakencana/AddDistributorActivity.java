package com.example.bayu.cvsuryakencana;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;

public class AddDistributorActivity extends ActionBarActivity {
    private EditText editTextNama, editTextAlamat, editTextKota, editTextTelp;
    private Button btnTambah, btnKembali;
    private static final String ADD_DISTRIBUTOR_URL = "http://10.0.3.2/tb-android/add_distributor.php";
    String name, address, city, telp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_distributor);

        editTextNama = (EditText) findViewById(R.id.etNameD);
        editTextAlamat = (EditText) findViewById(R.id.etAddressD);
        editTextKota = (EditText) findViewById(R.id.etCityD);
        editTextTelp = (EditText) findViewById(R.id.etTelpD);
        btnTambah = (Button) findViewById(R.id.btnAddD);
        btnKembali = (Button) findViewById(R.id.btnCancelD);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editTextNama.getText().toString();
                address = editTextAlamat.getText().toString();
                city = editTextKota.getText().toString();
                telp = editTextTelp.getText().toString();

                add_distributor(name, address, city, telp);
            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void add_distributor(String name, String address, String city, String telp) {
        class AddDistributor extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RequestHandler requestHandler = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddDistributorActivity.this, "Please Wait", "Loading...");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if (editTextNama.getText().toString().trim().length() == 0
                        || editTextAlamat.getText().toString().trim().length() == 0
                        || editTextKota.getText().toString().trim().length() == 0
                        || editTextTelp.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(AddDistributorActivity.this, DataDistributorActivity.class);
                    finish();
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("name",params[0]);
                data.put("address",params[1]);
                data.put("city",params[2]);
                data.put("telp",params[3]);

                String result = requestHandler.sendPostRequest(ADD_DISTRIBUTOR_URL,data);

                return  result;
            }
        }

        AddDistributor ad = new AddDistributor();
        ad.execute(name, address, city, telp);
    }
}
