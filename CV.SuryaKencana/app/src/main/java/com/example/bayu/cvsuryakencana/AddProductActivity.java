package com.example.bayu.cvsuryakencana;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class AddProductActivity extends ActionBarActivity {
    private EditText editTextNama, editTextDiskripsi, editTextKategori, editTextHarga, editTextStok;
    private Button btnTambah, btnKembali;
    private static final String ADD_PRODUCT_URL = "http://10.0.3.2/tb-android/add_product.php";
    String name, description, category, price, stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        editTextNama = (EditText) findViewById(R.id.etName2);
        editTextDiskripsi = (EditText) findViewById(R.id.etDesc);
        editTextKategori = (EditText) findViewById(R.id.etCategory);
        editTextHarga = (EditText) findViewById(R.id.etPrice);
        editTextStok = (EditText) findViewById(R.id.etStock);
        btnTambah = (Button) findViewById(R.id.btnAddProduct2);
        btnKembali = (Button) findViewById(R.id.btnCancel5);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editTextNama.getText().toString();
                description = editTextDiskripsi.getText().toString();
                category = editTextKategori.getText().toString();
                price = editTextHarga.getText().toString();
                stock = editTextStok.getText().toString();

                add_product(name, description, category, price, stock);
            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void add_product(String name, String description, String category, String price, String stock) {
        class AddProduct extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RequestHandler requestHandler = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddProductActivity.this, "Please Wait", "Loading...");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if (editTextNama.getText().toString().trim().length() == 0
                        || editTextDiskripsi.getText().toString().trim().length() == 0
                        || editTextKategori.getText().toString().trim().length() == 0
                        || editTextHarga.getText().toString().trim().length() == 0
                        || editTextStok.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(AddProductActivity.this, DataProductActivity.class);
                    finish();
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("name",params[0]);
                data.put("description",params[1]);
                data.put("category",params[2]);
                data.put("price",params[3]);
                data.put("stock",params[4]);

                String result = requestHandler.sendPostRequest(ADD_PRODUCT_URL,data);

                return  result;
            }
        }

        AddProduct ap = new AddProduct();
        ap.execute(name, description, category, price, stock);
    }
}
