package com.mu.bob.generate.copylistenerapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private Cursor mListCursor;
    private SimpleCursorAdapter mCursorAdapter;
    private ActionBarDrawerToggle mToggle;
    private static final String TAG = "MainActivityTAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(MainActivity.this, MainService.class));
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerList = (ListView) findViewById(R.id.list);
        mDrawerLayout.setDrawerShadow(R.mipmap.ic_launcher, GravityCompat.START);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, 0, 0);
        mToggle.syncState();
        mDrawerLayout.addDrawerListener(mToggle);
        mCursorAdapter = new SimpleCursorAdapter(this,R.layout.item_list,mListCursor,new String[]{"_id","name","phone"},new int[]{R.id.id,R.id.name,R.id.phone}, 1);
        mDrawerList.setAdapter(mCursorAdapter);

    }
}
