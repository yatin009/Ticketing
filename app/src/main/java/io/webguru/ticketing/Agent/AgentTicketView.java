package io.webguru.ticketing.Agent;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.webguru.ticketing.Contractor.ContractorMainActivity;
import io.webguru.ticketing.Global.GlobalFunctions;
import io.webguru.ticketing.POJO.AgentData;
import io.webguru.ticketing.POJO.ContractorData;
import io.webguru.ticketing.POJO.RequesterData;
import io.webguru.ticketing.POJO.Ticket;
import io.webguru.ticketing.POJO.UserInfo;
import io.webguru.ticketing.R;
import io.webguru.ticketing.Requester.RequesterMainActivity;

import static io.webguru.ticketing.Global.GlobalConstant.FILE_STORAGE_PATH;

public class AgentTicketView extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

//    @Bind(R.id.ticket_proof_image)
//    ImageView ticketImageProof;
    @Bind(R.id.progress_dialog)
    ProgressBar progressBar;
    @Bind(R.id.ticket_priority)
    TextView priorityView;
    @Bind(R.id.date_value)
    TextView dateView;

    @Bind(R.id.requester_name)
    TextView agentNameView;
    @Bind(R.id.ticket_number)
    TextView issueValue;
    @Bind(R.id.ticket_location)
    TextView locationView;
    @Bind(R.id.ticket_site)
    TextView shopView;
    @Bind(R.id.ticket_proof_image)
    ImageView issueImage;

    @Bind(R.id.assign_contractor_layout)
    LinearLayout assignContractorLayout;
    @Bind(R.id.scope_switch)
    Switch scopeSwitch;
    @Bind(R.id.scope_edit_value)
    TextView scopeEditValue;
    @Bind(R.id.contractor_employee_spinner)
    Spinner contractorNameEditView;
    @Bind(R.id.contractor_note_edit)
    TextInputEditText contractorNoteEditView;

    @Bind(R.id.assigned_contractor_layout)
    LinearLayout assignedContractorLayout;
    @Bind(R.id.assigned_date)
    TextView assignedDateView;
    @Bind(R.id.scope_value)
    TextView scopeView;
    @Bind(R.id.contractot_employee_list_label)
    TextView contractorEmployeeListLabel;
    @Bind(R.id.contractor_value)
    TextView contractorView;
    @Bind(R.id.contractor_note_value)
    TextView contractorNoteView;

    @Bind(R.id.contractor_qoute_layout)
    LinearLayout contractorQuoteLayout;
    @Bind(R.id.text_quote_labour)
    TextInputEditText quoteLabourEdit;
    @Bind(R.id.text_quote_parts)
    TextInputEditText quotePartsEdit;
    @Bind(R.id.text_qoute_tax)
    TextInputEditText quoteTaxEdit;
    @Bind(R.id.total_value)
    TextView totalTextValue;

    @Bind(R.id.contractor_quote_view_layout)
    LinearLayout contractorQuoteViewLayout;
//    @Bind(R.id.quoted_date)
//    TextView quotedDateView;
    @Bind(R.id.quote_price_value)
    TextView quotePriceView;
    @Bind(R.id.final_price_value)
    TextView finalPriceView;
    @Bind(R.id.variance_quote_value)
    TextView varianceQuoteView;
    @Bind(R.id.quote_parts_value)
    TextView quotePartsView;
    @Bind(R.id.quote_service_value)
    TextView quoteServiceView;
    @Bind(R.id.quote_others_value)
    TextView qupteOthersView;
    @Bind(R.id.call_contractor_button)
    ImageButton callContractor;
    @Bind(R.id.contractor_quote_date)
    TextView contractorQuoteDate;

    @Bind(R.id.internal_quote_layout)
    LinearLayout internalQuoteLayout;
    @Bind(R.id.final_price)
    TextInputEditText internalFinalPriceEdit;
    @Bind(R.id.variance_price)
    TextInputEditText internalVarinaceEdit;
    @Bind(R.id.approver_name)
    TextInputEditText approverNameEdit;


    private Ticket ticket;
    private RequesterData requesterData;
    private UserInfo userInfo;
    private AgentData agentData;
    private ContractorData contractorData;

    private String userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_ticket_view);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            userInfo = (UserInfo) bundle.get("UserInfo");
            ticket = (Ticket) bundle.get("Ticket");
        }
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Ticket # "+ticket.getTicketNumber());
        }
        userInfo = GlobalFunctions.getUserInfo(this);
        userRole = userInfo.getRole();
        requesterData = ticket.getRequester();
        setViews();
        spinnerValues();
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(FILE_STORAGE_PATH).child(ticket.getTicketNumber()+".jpg");
        storageRef.getDownloadUrl().addOnSuccessListener(this, new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(AgentTicketView.this).load(uri).listener(new RequestListener<Uri, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(issueImage);
            }
        });
        scopeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                Log.d("TICKETVIEW","switch >>> "+checked);
                if(checked){
                    scopeEditValue.setText("In");
                    contractorEmployeeListLabel.setText("Select a Employee");
                    contractorNameEditView.setAdapter(employeeAdapter);
                }else{
                    scopeEditValue.setText("Out");
                    contractorEmployeeListLabel.setText("Select a Contractor");
                    contractorNameEditView.setAdapter(contractorAdapter);
                }
            }
        });
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String taxValue = editable.toString();
                if("".equals(taxValue)){
                    quoteTaxEdit.setError("Please enter Tax value");
                    return;
                }
                String labourValue = quoteLabourEdit.getText().toString();
                if("".equals(labourValue)){
                    quoteLabourEdit.setError("Please enter a value");
                    return;
                }
                String partsValue = quotePartsEdit.getText().toString();
                if("".equals(partsValue)){
                    quotePartsEdit.setError("Please enter Tax value");
                    return;
                }
                double sum = Double.parseDouble(labourValue) + Double.parseDouble(partsValue);
                sum = sum + getTaxAmount(sum, Double.parseDouble(taxValue));
                totalTextValue.setText("$ "+sum);
            }
        };
        quoteTaxEdit.addTextChangedListener(textWatcher);
        quoteLabourEdit.addTextChangedListener(textWatcher);
        quotePartsEdit.addTextChangedListener(textWatcher);
    }

    public double getTaxAmount(double total, double tax){
        return tax/100 * total;
    }

    private void setViews(){
        if ("HIGH".equals(ticket.getPriority())) {
            priorityView.setTextColor(Color.RED);
        } else if ("MEDIUM".equals(ticket.getPriority())) {
            priorityView.setTextColor(Color.rgb(255, 165, 0));//Orange
        } else if ("LOW".equals(ticket.getPriority())) {
            priorityView.setTextColor(Color.BLACK);
        }
        priorityView.setText(ticket.getPriority());
        dateView.setText(ticket.getDateTime());

        agentNameView.setText(requesterData.getUserInfo().getFirstname());
        issueValue.setText(requesterData.getIssue());
        locationView.setText(requesterData.getLocation());
        shopView.setText(requesterData.getShop());

        if("manager".equals(userRole)) {
           if (ticket.getAgentData() != null) {
                assignedContractorLayout.setVisibility(View.VISIBLE);
                agentData = ticket.getAgentData();
                setAssignedContractorInfo();
            }else{
               assignContractorLayout.setVisibility(View.VISIBLE);
           }
            if(ticket.getContractorData()!=null){
//                internalQuoteLayout.setVisibility(View.VISIBLE);
                contractorData = ticket.getContractorData();
                contractorQuoteViewLayout.setVisibility(View.VISIBLE);
                setContractorQuoteInfo();
            }
        }else if("contractor".equals(userRole)){
            if(ticket.getContractorData()==null){
                contractorQuoteLayout.setVisibility(View.VISIBLE);
            }else if(ticket.getContractorData()!=null){
                callContractor.setVisibility(View.GONE);
                contractorData = ticket.getContractorData();
                contractorQuoteViewLayout.setVisibility(View.VISIBLE);
                setContractorQuoteInfo();
            }
        }
    }

    ArrayAdapter<String> contractorAdapter, employeeAdapter;
    private void spinnerValues(){
        String[] contractorSpinner = new String[] {
                "Contractor 1", "Contrctor 2", "Contrctor 3", "Contrctor 4", "Contrctor 5"
        };
        contractorAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, contractorSpinner);

        String[] employeeSpinner = new String[] {
                "Employee 1", "Employee 2", "Employee 3", "Employee 4", "Employee 5"
        };
        employeeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, employeeSpinner);
    }

    @OnClick(R.id.ticket_proof_image)
    public void onImageClick(){
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = getLayoutInflater().inflate(R.layout.dialog_image, null);
        dialog.setContentView(view);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress_dialog);
        final ImageView imageView = (ImageView) view.findViewById(R.id.large_image);
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(FILE_STORAGE_PATH).child(ticket.getTicketNumber()+".jpg");
        storageRef.getDownloadUrl().addOnSuccessListener(this, new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(AgentTicketView.this).load(uri).listener(new RequestListener<Uri, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(imageView);
            }
        });
        dialog.show();
    }

    @OnClick(R.id.call_contractor_button)
    public void callContractorButtonAction(){
        String requesterNumber = contractorData.getUserInfo().getNumber();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+requesterNumber));
        startActivity(intent);
    }

    @OnClick(R.id.call_contractor)
    public void callContractor(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:4164797440"));
        startActivity(intent);
    }

    @OnClick(R.id.assign_contractor_button)
    public void assignContractorButtonAction(){
        String contractorName = contractorNameEditView.getSelectedItem().toString();
        if("".equals(contractorName.trim())){
            Toast.makeText(this, "Please Select a value from spinner", Toast.LENGTH_LONG);
            return;
        }
        String contractorNote = contractorNoteEditView.getText().toString();
        if("".equals(contractorNote.trim())){
            contractorNoteEditView.setError("Invalid Note");
            return;
        }
        String scopeValue = scopeEditValue.getText().toString();
        AgentData agentData = new AgentData(scopeValue, contractorName, 3, contractorNote,
                GlobalFunctions.getCurrentDateTime(), userInfo);
        ticket.setAgentData(agentData);
        ticket.setContractorId(3);
        ticket.setStatus("Contractor Assigned");
        ticket.setAgent_status("1_Dispatched");

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/ticketing/" + ticket.getTicketNumber(), ticket.toMap());
        mDatabase.updateChildren(childUpdates);

        Intent intent = new Intent(this, AgentMainActivity.class);
        intent.putExtra("UserInfo",userInfo);
        startActivity(intent);
    }

    private void setAssignedContractorInfo(){
        assignedDateView.setText(agentData.getContractorAssignedDate());
        scopeView.setText(agentData.getScope());
        contractorView.setText(agentData.getContractor());
        contractorNoteView.setText(agentData.getContractorNote());
    }

    @OnClick(R.id.quote_button)
    public void quoteByContractorAction(){

        ContractorData contractorData = new ContractorData(GlobalFunctions.getCurrentDateTime(), Double.parseDouble(totalTextValue.getText().toString().split(" ")[1]),Double.parseDouble("0"),
                Double.parseDouble("0"), Double.parseDouble(quotePartsEdit.getText().toString()), Double.parseDouble("0"), Double.parseDouble("0"), userInfo);

        ticket.setContractorData(contractorData);
        ticket.setStatus("Contractor Quoted");
        ticket.setAgent_status("1_InternalQuote");

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/ticketing/" + ticket.getTicketNumber(), ticket.toMap());
        mDatabase.updateChildren(childUpdates);

        Intent intent = new Intent(this, ContractorMainActivity.class);
        intent.putExtra("UserInfo",userInfo);
        startActivity(intent);
    }

    private void setContractorQuoteInfo(){
        contractorQuoteDate.setText(contractorData.getQuotedDateTime());
        quotePriceView.setText("$ "+contractorData.getQuotePriceTotal());
        finalPriceView.setText("$ "+contractorData.getFinalPrice());
        varianceQuoteView.setText("$ "+contractorData.getVarianceToQuote());
        quotePartsView.setText("$ "+contractorData.getQuotedParts());
        quoteServiceView.setText("$ "+contractorData.getQuotedServices());
        qupteOthersView.setText("$ "+contractorData.getQuotedOthers());
    }

    @OnClick(R.id.request_approval)
    public void requestApproval(){
        GlobalFunctions.showToast(this, "Request for Approval is send.", Toast.LENGTH_LONG);
        startActivity(new Intent(this, AgentMainActivity.class));
    }
}
