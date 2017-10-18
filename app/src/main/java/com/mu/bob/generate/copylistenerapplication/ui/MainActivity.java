package com.mu.bob.generate.copylistenerapplication.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mu.bob.generate.copylistenerapplication.R;
import com.mu.bob.generate.copylistenerapplication.data.CopyBean;
import com.mu.bob.generate.copylistenerapplication.data.CopyListAdapter;
import com.mu.bob.generate.copylistenerapplication.presenter.MainPresenter;
import com.mu.bob.generate.copylistenerapplication.service.MainService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IMainView, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private List<CopyBean> mList;
    private CopyListAdapter mAdapter;
    private ActionBarDrawerToggle mToggle;
    private ClipboardManager clipboardManager;
    private static final String TAG = "MainActivityTAG";
    private MainPresenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter=new MainPresenter(this);
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
        mList=new ArrayList<>();
        mAdapter=new CopyListAdapter(this, mList);
        mToggle.syncState();
        mDrawerLayout.addDrawerListener(mToggle);
        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setOnItemClickListener(this);
        mDrawerList.setOnItemLongClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadList();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String text=mList.get(i).getText();
        Log.i(TAG, "MainActivity.onItemClick: text="+text);
        clipboardManager.setPrimaryClip(ClipData.newPlainText("click", text));
    }

    @Override
    public void refreshView(List<CopyBean> list) {
//        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        mPresenter.setLongClickPosition(i);
        view.showContextMenu();
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_del:
                mPresenter.clickDeleteItem(mList);
                break;
            case R.id.item_copy:
                break;
            case R.id.item_wait:
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }
}
