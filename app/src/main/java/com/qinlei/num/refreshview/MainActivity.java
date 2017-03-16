package com.qinlei.num.refreshview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mian);
    }

    public void addHeadAndFoot(View view) {
        startActivity(new Intent(this, AddHeadFootActivity.class));
    }

    public void loadMore(View view) {
        startActivity(new Intent(this, LoadMoreActivity.class));
    }
}
