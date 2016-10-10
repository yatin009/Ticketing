package io.webguru.ticketing.Global;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import io.webguru.ticketing.POJO.UserInfo;
import io.webguru.ticketing.R;

public class UserProfile extends AppCompatActivity {

    UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Bundle bundle = getIntent().getExtras();
        userInfo = (UserInfo) bundle.get("UserInfo");
        GlobalFunctions.showToast(this, "User - "+userInfo.getFirstname() +" "+ userInfo.getLastname(), Toast.LENGTH_LONG);
    }
}
