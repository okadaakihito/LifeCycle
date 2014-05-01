package com.example.lifecycle;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

public class BaseActivity extends ActionBarActivity {

    // アクティビティ
    public void loadScanner(View view) {
        Intent intent = new Intent(BaseActivity.this,
        		ScanActivity.class);
        startActivity(intent);
    }
    public void loadMypage(View view) {
        Intent intent = new Intent(BaseActivity.this,
        		TabActivity.class);
        startActivity(intent);
    }
    public void loadInformation(View view) {
        Intent intent = new Intent(BaseActivity.this,
        		InformationActivity.class);
        startActivity(intent);
    }
    public void loadOther(View view) {
        Intent intent = new Intent(BaseActivity.this,
        		OtherActivity.class);
        startActivity(intent);
    }
}
