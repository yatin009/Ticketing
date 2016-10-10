package io.webguru.ticketing.Welcome;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.webguru.ticketing.Approver.ApproverMainActivity;
import io.webguru.ticketing.Contractor.ContractorMainActivity;
import io.webguru.ticketing.DB.UserInfoDB;
import io.webguru.ticketing.FieldAgent.FieldAgentMainActivity;
import io.webguru.ticketing.Global.GlobalConstant;
import io.webguru.ticketing.Global.GlobalFunctions;
import io.webguru.ticketing.Manager.ManagerMainActivity;
import io.webguru.ticketing.POJO.UserAuth;
import io.webguru.ticketing.POJO.UserInfo;
import io.webguru.ticketing.R;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment {

    @Bind(R.id.sign_in_button)
    AppCompatButton signin;
    @Bind(R.id.editTextemail)
    EditText emailId;
    @Bind(R.id.editTextpassword)
    EditText password;
    private String TAG = "LOGINFRAGMENT";
    private DatabaseReference mDatabase, userInfoDatabaseRef;
    private ArrayList<UserAuth> userAuths = new ArrayList<>();

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    public void onStart(){
        super.onStart();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
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
                                                    GlobalFunctions.showToast(getActivity(), "You have already logged in on some other device.", Toast.LENGTH_LONG);
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
        });
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
            SQLiteDatabase db = getActivity().openOrCreateDatabase(GlobalConstant.DATABASE_NAME, MODE_PRIVATE, null);
            GlobalFunctions.upgrade_tables(getActivity(), db);
            userInfo.setLoggedin(true);
            return new UserInfoDB(getActivity()).addUserInfo(userInfo);
        }

        protected void onPostExecute(Boolean result){
            String msg = "User - "+userInfo.getFirstname() +" "+ userInfo.getLastname() + " Login successful.";
            if(result){
                if ("manager".equals(userInfo.getRole())) {
                    Intent intent = new Intent(getActivity(), ManagerMainActivity.class);
                    intent.putExtra("UserInfo", userInfo);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else if ("fieldagent".equals(userInfo.getRole())) {
                    Intent intent = new Intent(getActivity(), FieldAgentMainActivity.class);
                    intent.putExtra("UserInfo", userInfo);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else if ("contractor".equals(userInfo.getRole())) {
                    startActivity(new Intent(getActivity(), ContractorMainActivity.class));
                } else if ("approver".equals(userInfo.getRole())) {
                    startActivity(new Intent(getActivity(), ApproverMainActivity.class));
                }
                getActivity().finish();
            }else{
                msg = "User - "+userInfo.getFirstname() +" "+ userInfo.getLastname() + " Login un-successful.";
            }
            GlobalFunctions.showToast(getActivity(), msg, Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
