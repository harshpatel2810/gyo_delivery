package com.goyo.grocery_goyo;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.goyo.grocery.R;

import java.util.ArrayList;

public class FilterScreen extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerAdapterFilter mAdapter;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_screen);
        Intent io=getIntent();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerviewFilter);
        mAdapter = new RecyclerAdapterFilter(FilterScreen.this);
        mRecyclerView.setAdapter(mAdapter);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Filter");
    }
}
