package io.webguru.ticketing.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import io.webguru.ticketing.Global.GlobalConstant;
import io.webguru.ticketing.POJO.UserInfo;

/**
 * Created by yatin on 25/09/16.
 */

public class UserInfoDB extends SQLiteOpenHelper implements GlobalConstant {

    // Database Columns
    private final String primaryKey= "primary_key";
    private final String firstname= "first_name";
    private final String lastname = "last_name";
    private final String password = "password";
    private final String role = "role";
    private final String username = "username";
    private final String userid = "user_id";
    private final String isLoggedin = "is_Loggedin";

    public UserInfoDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_SHOP_LIST_OBJECT = "CREATE TABLE IF NOT EXISTS "+ TABLE_USER_INFO + "("
                + primaryKey + " INTEGER PRIMARY KEY,"
                + firstname + " TEXT,"
                + lastname + " TEXT,"
                + password + " TEXT,"
                + role + " TEXT,"
                + username + " TEXT,"
                + userid + " TEXT,"
                + isLoggedin + " BOOLEAN)";
        sqLiteDatabase.execSQL(CREATE_TABLE_SHOP_LIST_OBJECT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INFO);
        onCreate(sqLiteDatabase);
    }

    public boolean addUserInfo(UserInfo userInfo) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(firstname, userInfo.getFirstname());
            values.put(lastname, userInfo.getLastname());
            values.put(password, userInfo.getPassword());
            values.put(role, userInfo.getRole());
            values.put(username, userInfo.getUsername());
            values.put(userid, userInfo.getUserid());
            values.put(isLoggedin, userInfo.isLoggedin());
            db.insert(TABLE_USER_INFO, null, values);
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db!=null) {
                db.endTransaction();
                db.close();
            }
        } // Closing database connection
        return false;
    }

    public UserInfo getUserInfo() {
        UserInfo userInfo = null;
        String selectQuery = "SELECT  * FROM " + TABLE_USER_INFO + " LIMIT 1 ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    userInfo = new UserInfo();
                    userInfo.setFirstname(cursor.getString(1));
                    userInfo.setLastname(cursor.getString(2));
                    userInfo.setPassword(cursor.getString(3));
                    userInfo.setRole(cursor.getString(4));
                    userInfo.setUsername(cursor.getString(5));
                    userInfo.setUserid(cursor.getString(6));
                    userInfo.setLoggedin(cursor.getInt(7 )> 0);
                } while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (db!=null) {
                cursor.close();
                db.close();
            }
        }
        return userInfo;
    }

    public boolean updateUserSessionStatus(UserInfo userInfo) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(isLoggedin, userInfo.isLoggedin());
            db.update(TABLE_USER_INFO, values, userid + " = ?", new String[]{String.valueOf(userInfo.getUserid())});
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (db != null) {
                db.close();
            }
        } // Closing database connection
        return false;
    }

}
