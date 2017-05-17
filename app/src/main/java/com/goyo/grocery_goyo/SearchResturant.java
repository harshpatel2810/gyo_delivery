package com.goyo.grocery_goyo;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.goyo.grocery.R;

public class SearchResturant extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_resturant);
        Intent io=getIntent();
        InitAppBar();
    }
    public void InitAppBar()
    {
        ActionBar action=getSupportActionBar();
        action.setDisplayShowCustomEnabled(true);
        action.setCustomView(R.layout.search_for_resturant);
    }
}
