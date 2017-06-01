package com.goyo.grocery_goyo;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.goyo.grocery.R;
public class FilterScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_screen);
        Intent io=getIntent();
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Filter");
    }
}
