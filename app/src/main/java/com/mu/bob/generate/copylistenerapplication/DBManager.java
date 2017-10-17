package com.mu.bob.generate.copylistenerapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2017/10/16.
 */

public class DBManager {
    private static DBManager instance;
    public static DBManager getInstance(){
        if(instance==null){
            instance=new DBManager();
        }
        return instance;
    }

    private static final String TAG = "DBManagerTAG";
    private SQLiteDatabase database;
    public static final String FILE_NAME="copy.db";
    public static final String TABLE_NAME="texttable";
    private DBManager(){
        createDB();
    }

    private void createDB() {
        String dir= Environment.getExternalStorageDirectory()+ File.separator+"Android"+File.separator+"data"+File.separator+"com.mu.bob.generate.copylistenerapplication"+ File.separator+"databases";
        File dirFile=new File(dir);
        if(!dirFile.exists()){
            boolean mkdirs = dirFile.mkdirs();
            Log.i(TAG, "DBManager.DBManager: mk success="+mkdirs);
        }
        Log.i(TAG, "DBManager.DBManager: dir="+dir);
        File dbFile=new File(dir+File.separator+FILE_NAME);
        if(!dbFile.exists()){
            try {
                dbFile.createNewFile();
                database = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
                createTable();
            } catch (IOException e) {
                e.printStackTrace();
                Log.i(TAG, "DBManager.createDB: e="+e.getMessage());
            }
        }else {
            Log.i(TAG, "DBManager.DBManager: dbFile=" + dbFile.getAbsolutePath());
            database = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
        }
    }

    public void createTable(){
        String sql="create table "+TABLE_NAME+"(" +
                "_id integer primary key autoincrement," +
                "text text," +
                "time text)";
        if(database==null){
            createDB();
        }
        database.execSQL(sql);
    }

    public void dropTable(){
        String sql ="DROP TABLE "+TABLE_NAME;
        if(database==null){
            createDB();
        }
        database.execSQL(sql);
    }

    public void insert(CharSequence text){
        ContentValues cValue = new ContentValues();
        cValue.put("text", String.valueOf(text));
        cValue.put("time", System.currentTimeMillis());
        if(database==null){
            createDB();
        }
        database.insert(TABLE_NAME,null,cValue);
    }

    public void delete(int id) {
        String whereClause = "_id=?";
        String[] whereArgs = {String.valueOf(id)};
        if(database==null){
            createDB();
        }
        database.delete(TABLE_NAME,whereClause,whereArgs);
    }

    public void update( int id, ContentValues values) {
        String whereClause = "_id=?";
        String[] whereArgs={String.valueOf(id)};
        if(database==null){
            createDB();
        }
        database.update(TABLE_NAME,values,whereClause,whereArgs);
    }
    public Cursor queryAll() {
        if(database==null){
            createDB();
        }
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

    public Cursor query(int id) {
        String whereClause = "_id=?";
        String[] whereArgs={String.valueOf(id)};
        if(database==null){
            createDB();
        }
        Cursor cursor = database.query(TABLE_NAME, null, whereClause, whereArgs, null, null, null);
        return cursor;
    }


    }
