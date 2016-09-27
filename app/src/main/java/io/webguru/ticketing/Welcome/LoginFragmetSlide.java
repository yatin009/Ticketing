package io.webguru.ticketing.Welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.webguru.ticketing.Approver.ApproverMainActivity;
import io.webguru.ticketing.Contractor.ContractorMainActivity;
import io.webguru.ticketing.FieldAgent.FieldAgentMainActivity;
import io.webguru.ticketing.Manager.ManagerMainActivity;
import io.webguru.ticketing.POJO.UserAuth;
import io.webguru.ticketing.POJO.UserInfo;
import io.webguru.ticketing.R;


public class LoginFragmetSlide extends Fragment {

    @Bind(R.id.sign_in_button)
    AppCompatButton signin;
    @Bind(R.id.editTextemail)
    TextInputEditText emailId;
    @Bind(R.id.editTextpassword)
    TextInputEditText password;


    private String TAG = "LOGINFRAGMENT";
    private DatabaseReference mDatabase, userInfoDatabaseRef;
    private ArrayList<UserAuth> userAuths = new ArrayList<>();
    public static final int RC_SIGN_IN = 0;


    public LoginFragmetSlide() {
        // Required empty public constructor
    }

    public static LoginFragmetSlide newInstance(String param1, String param2) {
        LoginFragmetSlide fragment = new LoginFragmetSlide();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login_fragmet_slide, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void onStart() {
        super.onStart();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("user_auth");
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                userAuths.clear();
                mDatabase.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // Get user value
                                Log.d(TAG, "DATASNAP userAuth>>> " + dataSnapshot.toString());
                                Map<String, Map<String, String>> usersMap1 = (Map<String, Map<String, String>>) dataSnapshot.getValue();
                                for(String s : usersMap1.keySet()){
                                    Log.d(TAG,"outer key >> "+s);
                                    UserAuth userAuth = new UserAuth();
                                    Map<String, String> valueMap = usersMap1.get(s);
                                    for(String key : valueMap.keySet()){
                                        if("username".equals(key))
                                            userAuth.setUsername(valueMap.get(key));
                                        else if("password".equals(key))
                                            userAuth.setPassword(valueMap.get(key));
                                        else if("userid".equals(key))
                                            userAuth.setUserid(valueMap.get(key));
                                    }
                                    userAuths.add(userAuth);
                                }
                                Log.d(TAG,"user email >> "+emailId.getText().toString());
                                Log.d(TAG,"user password >> "+password.getText().toString());
                                for (UserAuth userAuth : userAuths){
                                    if(emailId.getText().toString().equals(userAuth.getUsername()) && password.getText().toString().equals(userAuth.getPassword())){
                                        userInfoDatabaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(userAuth.getUserid());
                                        userInfoDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                Log.d(TAG, "DATASNAP userinfo >>> " + dataSnapshot.toString());
                                                Map<String, String> usersInfoMap1 = (Map<String, String>) dataSnapshot.getValue();
                                                UserInfo userInfo = new UserInfo();
                                                for(String key : usersInfoMap1.keySet()){
                                                    if("username".equals(key))
                                                        userInfo.setUsername(usersInfoMap1.get(key));
                                                    else if("password".equals(key))
                                                        userInfo.setPassword(usersInfoMap1.get(key));
                                                    else if("firstname".equals(key))
                                                        userInfo.setFirstname(usersInfoMap1.get(key));
                                                    else if("lastname".equals(key))
                                                        userInfo.setLastname(usersInfoMap1.get(key));
                                                    else if("role".equals(key))
                                                        userInfo.setRole(usersInfoMap1.get(key));
                                                    else if("userid".equals(key))
                                                        userInfo.setUserid(usersInfoMap1.get(key));
                                                }
                                                if("manager".equals(userInfo.getRole())){
                                                    startActivity(new Intent(getContext(), ManagerMainActivity.class));
                                                } else if("fieldagent".equals(userInfo.getRole())){
                                                    Intent intent = new Intent(getContext(), FieldAgentMainActivity.class);
                                                    intent.putExtra("UserInfo", userInfo);
                                                    startActivity(intent);
                                                } else if("contractor".equals(userInfo.getRole())){
                                                    startActivity(new Intent(getContext(), ContractorMainActivity.class));
                                                } else if("approver".equals(userInfo.getRole())){
                                                    startActivity(new Intent(getContext(), ApproverMainActivity.class));
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
                                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                                // ...
                            }
                        });
//                startActivity(new Intent(getActivity(), FieldAgentMainActivity.class));
            }
        });
    }

    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
