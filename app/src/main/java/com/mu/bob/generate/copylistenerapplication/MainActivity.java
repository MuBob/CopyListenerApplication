package com.mu.bob.generate.copylistenerapplication;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private Cursor mListCursor;
    private SimpleCursorAdapter mCursorAdapter;
    private ActionBarDrawerToggle mToggle;
    private ClipboardManager clipboardManager;
    private static final String TAG = "MainActivityTAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(MainActivity.this, MainService.class));
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
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
        mCursorAdapter = new SimpleCursorAdapter(this,R.layout.item_list, null, new String[]{"_id","text","time"},new int[]{R.id.id,R.id.text,R.id.time}, 1);
        mDrawerList.setAdapter(mCursorAdapter);
        mListCursor=DBManager.getInstance().queryAll();
        mCursorAdapter.changeCursor(mListCursor);
        mDrawerList.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mListCursor=DBManager.getInstance().queryAll();
        mCursorAdapter.changeCursor(mListCursor);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mListCursor.moveToPosition(i);
        String text = mListCursor.getString(mListCursor.getColumnIndex("text"));
        Log.i(TAG, "MainActivity.onItemClick: text="+text);
        clipboardManager.setPrimaryClip(ClipData.newPlainText("click", text));
    }
}
