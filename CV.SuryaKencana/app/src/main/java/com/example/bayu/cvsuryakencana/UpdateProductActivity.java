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

public class UpdateProductActivity extends ActionBarActivity {
    private EditText editTextId, editTextNama, editTextDiskripsi, editTextKategori, editTextHarga, editTextStok;
    private Button buttonUpdate, buttonHapus, buttonKembali;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        Intent intent = getIntent();

        id = intent.getStringExtra("product_id");

        editTextId = (EditText) findViewById(R.id.etId);
        editTextNama = (EditText) findViewById(R.id.etName3);
        editTextDiskripsi = (EditText) findViewById(R.id.etDesc2);
        editTextKategori = (EditText) findViewById(R.id.etCategory2);
        editTextHarga = (EditText) findViewById(R.id.etPrice2);
        editTextStok = (EditText) findViewById(R.id.etStock2);
        buttonUpdate = (Button) findViewById(R.id.btnUpdateProduct);
        buttonHapus = (Button) findViewById(R.id.btnDeleteProduct);
        buttonKembali = (Button) findViewById(R.id.btnCancel6);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextNama.getText().toString().trim().length() == 0
                        || editTextDiskripsi.getText().toString().trim().length() == 0
                        || editTextKategori.getText().toString().trim().length() == 0
                        || editTextHarga.getText().toString().trim().length() == 0
                        || editTextStok.getText().toString().trim().length() == 0) {
                    Toast.makeText(UpdateProductActivity.this, "Mohon datanya dilengkapi terlebih dahulu!", Toast.LENGTH_LONG).show();
                } else {
                    updateProduct();
                    startActivity(new Intent(UpdateProductActivity.this, DataProductActivity.class));
                    finish();
                }
            }
        });

        buttonHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteProduct();
            }
        });

        buttonKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editTextId.setText(id);
        getProduct();
    }

    private void getProduct(){
        class GetProduct extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateProductActivity.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showProduct(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam("http://10.0.3.2/tb-android/get_product.php?id=",id);
                return s;
            }
        }
        GetProduct gp = new GetProduct();
        gp.execute();
    }

    private void showProduct(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject c = result.getJSONObject(0);
            String name = c.getString("name");
            String description = c.getString("description");
            String category = c.getString("category");
            String price = c.getString("price");
            String stock = c.getString("stock");

            editTextNama.setText(name);
            editTextDiskripsi.setText(description);
            editTextKategori.setText(category);
            editTextHarga.setText(price);
            editTextStok.setText(stock);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void updateProduct(){
        final String name = editTextNama.getText().toString();
        final String description = editTextDiskripsi.getText().toString();
        final String category = editTextKategori.getText().toString();
        final String price = editTextHarga.getText().toString();
        final String stock = editTextStok.getText().toString();

        class UpdateProduct extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateProductActivity.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(UpdateProductActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap();
                hashMap.put("id",id);
                hashMap.put("name",name);
                hashMap.put("description",description);
                hashMap.put("category",category);
                hashMap.put("price",price);
                hashMap.put("stock",stock);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest("http://10.0.3.2/tb-android/update_product.php", hashMap);

                return s;
            }
        }

        UpdateProduct up = new UpdateProduct();
        up.execute();
    }

    private void deleteProduct(){
        class DeleteProduct extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateProductActivity.this, "Deleting...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(UpdateProductActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam("http://10.0.3.2/tb-android/delete_product.php?id=", id);
                return s;
            }
        }

        DeleteProduct dp = new DeleteProduct();
        dp.execute();
    }

    private void confirmDeleteProduct(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah anda ingin mengapus data ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteProduct();
                        startActivity(new Intent(UpdateProductActivity.this, DataProductActivity.class));
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
