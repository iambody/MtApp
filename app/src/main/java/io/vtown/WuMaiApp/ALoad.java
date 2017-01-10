package io.vtown.WuMaiApp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.vtown.WuMaiApp.ui.ui.AMain;

public class ALoad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aload);
        startActivity(new Intent(ALoad.this, AMain.class).putExtra("from", 0));
    }
}
