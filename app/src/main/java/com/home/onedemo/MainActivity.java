package com.home.onedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.home.onedemo.App.UToast;
import com.home.onedemo.App.UtilSystem;

public class MainActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.sample_text);

    }

    public void btnClick(View view) {
        UToast.showText("20");
        tv.setText("版本号:" + UtilSystem.getVersionCode(this));
    }

}
