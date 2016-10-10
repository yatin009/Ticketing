package io.webguru.ticketing.FieldAgent;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.webguru.ticketing.Global.Chat;
import io.webguru.ticketing.POJO.FieldAgentData;
import io.webguru.ticketing.POJO.UserInfo;
import io.webguru.ticketing.R;

public class ViewEditFieldTicket extends AppCompatActivity {

    @Bind(R.id.valuePriority)
    TextView valuePriority;
    @Bind(R.id.valueDescription)
    TextView valueDescription;
    @Bind(R.id.valueStatus)
    TextView valueStatus;
    @Bind(R.id.valueLocation)
    TextView valueLocation;
    @Bind(R.id.valueShop)
    TextView valueShop;
    @Bind(R.id.valueScope)
    TextView valueScope;
    @Bind(R.id.valuessrType)
    TextView valuessrType;

    @Bind(R.id.text_site)
    TextInputEditText text_site;
    @Bind(R.id.text_shop)
    TextInputEditText text_shop;
    @Bind(R.id.text_priority)
    TextInputEditText text_priority;
    @Bind(R.id.text_location)
    TextInputEditText text_location;
    @Bind(R.id.text_description)
    TextInputEditText text_description;
    @Bind(R.id.text_scope)
    TextInputEditText text_scope;
    @Bind(R.id.text_ssrtype)
    TextInputEditText text_ssrtype;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.viewFlipper)
    ViewFlipper viewFlipper;

    private boolean isEditVisible = false;
    private UserInfo userInfo;
    private FieldAgentData fieldAgentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit_field_ticket);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Bundle bundle = getIntent().getExtras();
        //TODO Handle null case
        if(bundle!=null){
            userInfo = (UserInfo) bundle.get("UserInfo");
            fieldAgentData = (FieldAgentData) bundle.get("FieldAgentData");
        }
        setViewData();
        setEditValue();
    }

    private void setViewData(){
        valueLocation.setText(fieldAgentData.getLocation());
        valueDescription.setText(fieldAgentData.getDescription());
        valuePriority.setText(fieldAgentData.getPriority());

        valueStatus.setText(fieldAgentData.getStatus());
        valueShop.setText(fieldAgentData.getShop());
        valueScope.setText(fieldAgentData.getScope());
        valuessrType.setText(fieldAgentData.getSsrType());
    }

    private void setEditValue(){
    }

    @OnClick(R.id.button_chat)
    public void initiateChat(){
        Intent intent = new Intent(this, Chat.class);
        intent.putExtra("UserInfo", userInfo);
        intent.putExtra("FieldAgentData", fieldAgentData);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_edit_field_ticket, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_edit:
                flipViewEdit();
                if(!isEditVisible) {
                    isEditVisible = true;
                    item.setIcon(R.drawable.ic_visibility_white_24dp);
                }else{
                    isEditVisible = false;
                    // TODO: 27/09/16  HIDE KEYBOARD when switching views.
                    item.setIcon(R.drawable.ic_mode_edit_white_24dp);
                }
                break;

            case android.R.id.home:
                onBackPressed();
//                Intent intent = new Intent(ViewEditFieldTicket.this, FieldAgentMainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.putExtra("UserInfo", userInfo);
//                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void flipViewEdit(){
        viewFlipper.showNext();
        viewFlipper.setFlipInterval(500);
    }
}
