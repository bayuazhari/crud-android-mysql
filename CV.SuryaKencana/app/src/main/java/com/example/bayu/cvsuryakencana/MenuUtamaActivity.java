package com.example.bayu.cvsuryakencana;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuUtamaActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);

        Intent i = getIntent();
        String username = i.getStringExtra(LoginActivity.USER_NAME);

        TextView tvWelcome = (TextView) findViewById(R.id.textView4);
        Button buttonProduk = (Button) findViewById(R.id.btnProduct);
        Button buttonPemesanan = (Button) findViewById(R.id.btnDistributor);
        Button buttonUser = (Button) findViewById(R.id.btnUser);
        Button buttonLogout = (Button) findViewById(R.id.btnLogout);

        tvWelcome.setText("Welcome " + username);

        buttonProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent idp = new Intent(MenuUtamaActivity.this, DataProductActivity.class);
                startActivity(idp);
            }
        });

        buttonPemesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ido = new Intent(MenuUtamaActivity.this, DataDistributorActivity.class);
                startActivity(ido);
            }
        });

        buttonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent idu = new Intent(MenuUtamaActivity.this, DataUserActivity.class);
                startActivity(idu);
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent idl = new Intent(MenuUtamaActivity.this, LoginActivity.class);
                finish();
                startActivity(idl);
            }
        });
    }
}
