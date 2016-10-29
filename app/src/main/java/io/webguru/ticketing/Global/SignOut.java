package io.webguru.ticketing.Global;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.webguru.ticketing.Agent.AgentMainActivity;
import io.webguru.ticketing.Approver.ApproverMainActivity;
import io.webguru.ticketing.Contractor.ContractorMainActivity;
import io.webguru.ticketing.DB.UserInfoDB;
import io.webguru.ticketing.Requester.RequesterMainActivity;
import io.webguru.ticketing.POJO.UserInfo;
import io.webguru.ticketing.R;
import io.webguru.ticketing.Welcome.SplashScreen;

public class SignOut extends AppCompatActivity {

    private UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_out);
        userInfo = GlobalFunctions.getUserInfo(this);
        userInfo.setLoggedin(false);
        signOutUserFormServer();
    }

    private void signOutUserFormServer(){
        DatabaseReference updateUserStatusDatabaseRef = FirebaseDatabase.getInstance().getReference();
        updateUserStatusDatabaseRef.child("users").orderByChild("userid").equalTo(userInfo.getUserid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null && dataSnapshot.getValue()!=null) {
                    for (DataSnapshot userInfoSnapshot : dataSnapshot.getChildren()) {
                        userInfoSnapshot.getRef().child("isLoggedin").setValue(false);
                    }
                    new SignOutUserInDB().execute();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private class SignOutUserInDB extends AsyncTask<Void, Void ,Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            return new UserInfoDB(SignOut.this).updateUserSessionStatus(userInfo);
        }

        protected void onPostExecute(Boolean result){
            String msg = "User - "+userInfo.getFirstname() +" "+ userInfo.getLastname() + " Logout successful.";
            if(result){
                Intent intent = new Intent(SignOut.this, SplashScreen.class);
                intent.addFlags((Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                startActivity(intent);
            }else{
                msg = "User - "+userInfo.getFirstname() +" "+ userInfo.getLastname() + " Logout un-successful.";
                userInfo.setLoggedin(true);
                //TODO update server also with unsuccessfull logout i.e reverse signOutUserFormServer() function
                if ("manager".equals(userInfo.getRole())) {
                    Intent intent = new Intent(SignOut.this, AgentMainActivity.class);
                    intent.putExtra("UserInfo", userInfo);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else if ("fieldagent".equals(userInfo.getRole())) {
                    Intent intent = new Intent(SignOut.this, RequesterMainActivity.class);
                    intent.putExtra("UserInfo", userInfo);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else if ("contractor".equals(userInfo.getRole())) {
                    startActivity(new Intent(SignOut.this, ContractorMainActivity.class));
                } else if ("approver".equals(userInfo.getRole())) {
                    startActivity(new Intent(SignOut.this, ApproverMainActivity.class));
                }
            }
            GlobalFunctions.showToast(SignOut.this, msg, Toast.LENGTH_LONG);
        }
    }

}
