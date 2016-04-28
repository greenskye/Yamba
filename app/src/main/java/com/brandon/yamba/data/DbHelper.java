package com.brandon.yamba.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by BHARBO3 on 4/28/2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String TAG = "DbHelper";

    public DbHelper(Context context) {
        super(context, StatusContract.DB_NAME, null, StatusContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("create table %s ("
                + "%s int primary key, "    // ID
                + "%s text, "               // User
                + "%s text, "               // Message
                + "%s int"                  // Created At
                + ")",
                StatusContract.TABLE,
                StatusContract.Column.ID,
                StatusContract.Column.USER,
                StatusContract.Column.MESSAGE,
                StatusContract.Column.CREATED_AT
        );

        Log.d(TAG, "Timeline DB Create Table for Status: " + sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + StatusContract.TABLE);
        onCreate(db);
    }

    // TODO - provide DAO interface
//    public YambaClient.Status insert(YambaClient.Status status){
//        return null;
//    }
//
//    public YambaClient.Status delete(YambaClient.Status status){
//        return null;
//    }
//
//    public YambaClient.Status update(YambaClient.Status status){
//        return null;
//    }
//
//    public List<YambaClient.Status> findAll() {
//        return null;
//    }
//
//    public List<YambaClient.Status> findByUser(String user) {
//        return null;
//    }
}
