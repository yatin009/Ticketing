package io.webguru.ticketing.Global;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yatin on 25/09/16.
 */

public class GlobalFunctions {

    public static void showToast(Context context, String message, int duration){
         Toast.makeText(context, message, duration).show();
    }

    public static String getCurrentDateTime(){
        return DateFormat.getDateInstance(DateFormat.LONG).format(new Date());
    }

    public static String getTime(){
        return "Time Exception";//DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date());
    }

    public static String getCurrentDateInMilliseconds(String append){
        return new Date().getTime()+append;
    }
}
