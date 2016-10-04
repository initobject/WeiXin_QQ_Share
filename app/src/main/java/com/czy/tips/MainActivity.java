package com.czy.tips;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.czy.tips.qqapi.QQActivity;
import com.czy.tips.wxapi.WXEntryActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void shareToWeiXin(View view) {
        startActivity(new Intent(this, WXEntryActivity.class));
    }

    public void shareToQQ(View view) {
        startActivity(new Intent(this, QQActivity.class));
    }

}
