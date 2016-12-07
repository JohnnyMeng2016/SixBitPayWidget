package com.johnny.sixbitpaywidget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button showPayBtn = (Button) findViewById(R.id.btn_show_pay);
        showPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PasswordActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.keyboard_show, R.anim.keyboard_hide);
            }
        });
    }

}
