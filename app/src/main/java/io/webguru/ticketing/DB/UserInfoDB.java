package io.webguru.ticketing.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import io.webguru.ticketing.Global.GlobalConstant;

/**
 * Created by yatin on 25/09/16.
 */

public class UserInfoDB extends SQLiteOpenHelper implements GlobalConstant {

    public UserInfoDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
