package io.webguru.ticketing.Global;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.icu.text.SimpleDateFormat;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import io.webguru.ticketing.DB.UserInfoDB;
import io.webguru.ticketing.POJO.UserInfo;

/**
 * Created by yatin on 25/09/16.
 */

public class GlobalFunctions {

    public static void showToast(Context context, String message, int duration){
         Toast.makeText(context, message, duration).show();
    }

    public static String getCurrentDateTime(){
        java.text.SimpleDateFormat formattedDt = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm aaa", Locale.US);
        return formattedDt.format(new Date());
    }

    public static String getTodaysDateFormatted() {
        java.text.SimpleDateFormat formattedDt = new java.text.SimpleDateFormat("dd-MM-yyyy", Locale.US);
        return formattedDt.format(new Date());
    }

    public static String getTime(){
        return "Time Exception";//DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date());
    }

    public static String getCurrentDateInMilliseconds(String append){
        return new Date().getTime()+append;
    }

    public static boolean checkDataBase(Context context) {
        SQLiteDatabase checkDB = null;
        try {

            checkDB = SQLiteDatabase.openDatabase(context.getDatabasePath(GlobalConstant.DATABASE_NAME).toString(), null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return checkDB != null;
    }

    public static void upgrade_tables(Context con, SQLiteDatabase db) {
        new UserInfoDB(con).onUpgrade(db, 0, 0);
        db.close();
    }

    public static UserInfo getUserInfo(Context context){
        return new UserInfoDB(context).getUserInfo();
    }

}
