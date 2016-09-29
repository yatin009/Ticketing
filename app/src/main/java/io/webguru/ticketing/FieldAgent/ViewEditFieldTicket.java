package io.webguru.ticketing.FieldAgent;

import android.content.Intent;
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
import io.webguru.ticketing.POJO.FieldAgentData;
import io.webguru.ticketing.POJO.UserInfo;
import io.webguru.ticketing.R;

public class ViewEditFieldTicket extends AppCompatActivity {

    @Bind(R.id.valueFactory)
    TextView valueFactory;
    @Bind(R.id.valuePhoto)
    ImageView valuePhoto;
    @Bind(R.id.valuePriorityValue)
    TextView valuePriorityValue;
    @Bind(R.id.valueProblem)
    TextView valueProblem;

    @Bind(R.id.editTextProblem)
    EditText editTextProblem;
    @Bind(R.id.seekBarPriority)
    SeekBar seekBarPriority;
    @Bind(R.id.textViewPriorityValue)
    TextView textViewPriorityValue;
    @Bind(R.id.spinnerFactory)
    Spinner spinnerFactory;
    @Bind(R.id.buttonPhoto)
    Button buttonTakePhoto;
    @Bind(R.id.imageViewPhoto)
    ImageView imageViewPhoto;
    @Bind(R.id.buttonRequestTicket)
    Button buttonRequestTicket;
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
        valueFactory.setText(fieldAgentData.getLocation());
        valueProblem.setText(fieldAgentData.getProblem());
        valuePriorityValue.setText(fieldAgentData.getPriority());
    }

    private void setEditValue(){
        editTextProblem.setText(fieldAgentData.getProblem());
        String[] locationValue =  getResources().getStringArray(R.array.factory_list);
        int count=0;
        for(String s : locationValue){
            if(s.equals(fieldAgentData.getLocation()))
                break;
            else
                count++;
        }
        spinnerFactory.setSelection(count);
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
                Intent intent = new Intent(ViewEditFieldTicket.this, FieldAgentMainActivity.class);
                intent.putExtra("UserInfo", userInfo);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void flipViewEdit(){
        viewFlipper.showNext();
        viewFlipper.setFlipInterval(500);
    }
}
