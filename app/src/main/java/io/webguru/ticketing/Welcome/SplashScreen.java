package io.webguru.ticketing.Welcome;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.webguru.ticketing.Agent.AgentMainActivity;
import io.webguru.ticketing.Approver.ApproverMainActivity;
import io.webguru.ticketing.Contractor.ContractorMainActivity;
import io.webguru.ticketing.DB.UserInfoDB;
import io.webguru.ticketing.Global.GlobalConstant;
import io.webguru.ticketing.POJO.UserAuth;
import io.webguru.ticketing.Requester.AddTicketRequest;
import io.webguru.ticketing.Requester.RequesterMainActivity;
import io.webguru.ticketing.Global.GlobalFunctions;
import io.webguru.ticketing.POJO.UserInfo;
import io.webguru.ticketing.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreen extends AppCompatActivity {

    private String TAG = "SPLASHSCREEN";

    @Bind(R.id.parent_layout)
    LinearLayout parentLayout;
    @Bind(R.id.ticket_layout)
    RelativeLayout ticketLayout;
    @Bind(R.id.editTextemail)
    EditText emailId;
    @Bind(R.id.editTextpassword)
    EditText password;
    private DatabaseReference mDatabase, userInfoDatabaseRef;
    private ArrayList<UserAuth> userAuths = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);

        // Set up the user interaction to manually show or hide the system UI.
        parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = parentLayout.getRootView().getHeight() - parentLayout.getHeight();
                if (heightDiff > dpToPx(SplashScreen.this, 200)) { // if more than 200 dp, it's probably a keyboard...
                    // ... do something here
                    ticketLayout.setVisibility(View.GONE);
                }else{
                    ticketLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference();
        new CheckUserInDB().execute();
    }

    public float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    public void onStart(){
        super.onStart();
    }

//    private void animation1() {
//        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(mContentView, "scaleX", 1.0F, 0.0F);
//        scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
//        scaleXAnimation.setDuration(1200);
//        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(mContentView, "scaleY", 1.0F, 0.0F);
//        scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
//        scaleYAnimation.setDuration(1200);
//        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(mContentView, "alpha", 1.0F, 0.0F);
//        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
//        alphaAnimation.setDuration(1200);
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
//        animatorSet.setStartDelay(500);
//        animatorSet.start();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                new CheckUserInDB().execute();
//            }
//        }, 1000);
//    }

    private class CheckUserInDB extends AsyncTask<Void, Void ,Boolean> {

        UserInfo userInfo;
        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean dbExist = GlobalFunctions.checkDataBase(SplashScreen.this);
            Log.d(TAG, "dbExist >>> "+dbExist);
            if(!dbExist)
                return false;
            userInfo = new UserInfoDB(SplashScreen.this).getUserInfo();
            return (userInfo!=null && userInfo.isLoggedin());
        }

        protected void onPostExecute(Boolean result){
            if(!result){
//                Intent i = new Intent(SplashScreen.this, IntroScreens.class);
//                startActivity(i);
            }else {
                if ("manager".equals(userInfo.getRole())) {
                    Intent intent = new Intent(SplashScreen.this, AgentMainActivity.class);
                    intent.putExtra("UserInfo", userInfo);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else if ("fieldagent".equals(userInfo.getRole())) {
                    Intent intent = new Intent(SplashScreen.this, RequesterMainActivity.class);
                    intent.putExtra("UserInfo", userInfo);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else if ("contractor".equals(userInfo.getRole())) {
                    startActivity(new Intent(SplashScreen.this, ContractorMainActivity.class));
                } else if ("approver".equals(userInfo.getRole())) {
                    startActivity(new Intent(SplashScreen.this, ApproverMainActivity.class));
                }
                (SplashScreen.this).finish();
            }
        }
    }

    @OnClick(R.id.generate_ticket)
    public void addTicket(){
        startActivity(new Intent(SplashScreen.this, AddTicketRequest.class));
    }

    @OnClick(R.id.call_ticket)
    public void callCenterTicket(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:4164797440"));
        startActivity(intent);
    }

    @OnClick(R.id.login_button)
    public void login(){
        userAuths.clear();
        String emailID = emailId.getText().toString();
        String passwordText = password.getText().toString();
        Log.d(TAG, "user email >> " + emailID);
        Log.d(TAG, "user password >> " + passwordText);
        if("".equals(emailID.trim())){
            emailId.setError("Please enter your username");
            return;
        }else if("".equals(passwordText.trim())){
            password.setError("Please enter your password");
            return;
        }
        mDatabase.child("user_auth").orderByChild("username_password").equalTo(emailID+"_"+passwordText).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null && dataSnapshot.getValue()!=null){
                    UserAuth userAuth = null;
                    for (DataSnapshot userAuthSnapshot: dataSnapshot.getChildren()) {
                        userAuth = userAuthSnapshot.getValue(UserAuth.class);
                    }
                    if(userAuth!=null) {
                        userInfoDatabaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(userAuth.getUserid());
                        userInfoDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot!=null && dataSnapshot.getValue()!=null) {
                                    Log.d(TAG, "userInfo dataSnapshot.getValue().toString() >> " + dataSnapshot.getValue().toString());
                                    UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                                    if(userInfo!=null) { //&& !userInfo.isLoggedin()
                                        Log.d(TAG, "user userInfo.isLoggedin() >> " + userInfo.isLoggedin());
                                        if(userInfo.isLoggedin()){
                                            GlobalFunctions.showToast(SplashScreen.this, "You have already logged in on some other device.", Toast.LENGTH_LONG);
                                            return;
                                        }
                                        updateUserLoginStatus(userInfo);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @OnClick(R.id.sign_up)
    public void signUp(){
        Toast.makeText(getApplicationContext(),"SignUp Feature selected",Toast.LENGTH_SHORT).show();
    }

    private void updateUserLoginStatus(final UserInfo userInfo){
        DatabaseReference updateUserStatusDatabaseRef = FirebaseDatabase.getInstance().getReference();
        updateUserStatusDatabaseRef.child("users").orderByChild("userid").equalTo(userInfo.getUserid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null && dataSnapshot.getValue()!=null){
                    for (DataSnapshot userInfoSnapshot: dataSnapshot.getChildren()) {
                        userInfoSnapshot.getRef().child("isLoggedin").setValue(true);
                    }
                    new LoginUserInDB(userInfo).execute();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private class LoginUserInDB extends AsyncTask<Void, Void ,Boolean> {

        private UserInfo userInfo;

        LoginUserInDB(UserInfo userInfo){
            this.userInfo = userInfo;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            SQLiteDatabase db = openOrCreateDatabase(GlobalConstant.DATABASE_NAME, MODE_PRIVATE, null);
            GlobalFunctions.upgrade_tables(SplashScreen.this, db);
            userInfo.setLoggedin(true);
            return new UserInfoDB(SplashScreen.this).addUserInfo(userInfo);
        }

        protected void onPostExecute(Boolean result){
            String msg = "User - "+userInfo.getFirstname() +" "+ userInfo.getLastname() + " Login successful.";
            if(result){
                if ("manager".equals(userInfo.getRole())) {
                    Intent intent = new Intent(SplashScreen.this, AgentMainActivity.class);
                    intent.putExtra("UserInfo", userInfo);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else if ("fieldagent".equals(userInfo.getRole())) {
                    Intent intent = new Intent(SplashScreen.this, RequesterMainActivity.class);
                    intent.putExtra("UserInfo", userInfo);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else if ("contractor".equals(userInfo.getRole())) {
                    Intent intent = new Intent(SplashScreen.this, ContractorMainActivity.class);
                    intent.putExtra("UserInfo", userInfo);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else if ("approver".equals(userInfo.getRole())) {
                    startActivity(new Intent(SplashScreen.this, ApproverMainActivity.class));
                }
                SplashScreen.this.finish();
            }else{
                msg = "User - "+userInfo.getFirstname() +" "+ userInfo.getLastname() + " Login un-successful.";
            }
            GlobalFunctions.showToast(SplashScreen.this, msg, Toast.LENGTH_LONG);
        }
    }



}
