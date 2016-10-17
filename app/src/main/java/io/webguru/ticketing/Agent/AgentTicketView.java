package io.webguru.ticketing.Agent;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

public class AgentTicketView extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.priority_value)
    TextView priorityView;
    @Bind(R.id.date_value)
    TextView dateView;

    @Bind(R.id.agent_name_value)
    TextView agentNameView;
    @Bind(R.id.issue_value)
    TextView issueValue;
    @Bind(R.id.location_value)
    TextView locationView;
    @Bind(R.id.shop_value)
    TextView shopView;

    @Bind(R.id.assign_contractor_layout)
    LinearLayout assignContractorLayout;
    @Bind(R.id.scope_edit)
    TextInputEditText scopeEditView;
    @Bind(R.id.contractor_edit)
    TextInputEditText contractorNameEditView;
    @Bind(R.id.contractor_note_edit)
    TextInputEditText contractorNoteEditView;

    @Bind(R.id.assigned_contractor_layout)
    LinearLayout assignedContractorLayout;
    @Bind(R.id.assigned_date)
    TextView assignedDateView;
    @Bind(R.id.scope_value)
    TextView scopeView;
    @Bind(R.id.contractor_value)
    TextView contractorView;
    @Bind(R.id.contractor_note_value)
    TextView contractorNoteView;

    @Bind(R.id.contractor_qoute_layout)
    LinearLayout contractorQuoteLayout;
    @Bind(R.id.text_quote_total)
    TextInputEditText quoteToatlEdit;
    @Bind(R.id.text_final_price)
    TextInputEditText finalPriceEdit;
    @Bind(R.id.text_variance_quote)
    TextInputEditText varianceQuoteEdit;
    @Bind(R.id.text_quote_parts)
    TextInputEditText quotePartsEdit;
    @Bind(R.id.text_quote_service)
    TextInputEditText quoteServiceEdit;
    @Bind(R.id.text_quote_others)
    TextInputEditText quoteOthersEdit;

    @Bind(R.id.contractor_quote_view_layout)
    LinearLayout contractorQuoteViewLayout;
    @Bind(R.id.quoted_date)
    TextView quotedDateView;
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
    AppCompatButton callContractor;

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
            getSupportActionBar().setTitle(ticket.getTicketNumber());
        }
        userInfo = GlobalFunctions.getUserInfo(this);
        userRole = userInfo.getRole();
        requesterData = ticket.getRequester();
        setViews();
    }

    private void setViews(){

        priorityView.setText(ticket.getPriority());
        dateView.setText(ticket.getDateTime());

        agentNameView.setText(requesterData.getUserInfo().getFirstname());
        issueValue.setText(requesterData.getIssue());
        locationView.setText(requesterData.getLocation());
        shopView.setText(requesterData.getShop());

        if("manager".equals(userRole)) {
           if (ticket.getAgentData() != null) {
                assignContractorLayout.setVisibility(View.GONE);
                assignedContractorLayout.setVisibility(View.VISIBLE);
                agentData = ticket.getAgentData();
                setAssignedContractorInfo();
            }
            if(ticket.getContractorData()!=null){
                internalQuoteLayout.setVisibility(View.VISIBLE);
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

    @OnClick(R.id.call_requester_button)
    public void callRequesterButtonAction(){
        String requesterNumber = requesterData.getUserInfo().getNumber();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+requesterNumber));
        startActivity(intent);
    }

    @OnClick(R.id.call_contractor_button)
    public void callContractorButtonAction(){
        String requesterNumber = contractorData.getUserInfo().getNumber();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+requesterNumber));
        startActivity(intent);
    }

    @OnClick(R.id.assign_contractor_button)
    public void assignContractorButtonAction(){
        String scopeValue = scopeEditView.getText().toString();
        if("".equals(scopeValue.trim())){
            scopeEditView.setError("Invalid Scope");
            return;
        }
        String contractorName = contractorNameEditView.getText().toString();
        if("".equals(contractorName.trim())){
            contractorNameEditView.setError("Invalid Name");
        }
        String contractorNote = contractorNoteEditView.getText().toString();
        if("".equals(contractorNote.trim())){
            contractorNoteEditView.setError("Invalid Note");
        }
        AgentData agentData = new AgentData(scopeValue, contractorName, 3, contractorNote,
                GlobalFunctions.getCurrentDateTime(), userInfo);
        ticket.setAgentData(agentData);
        ticket.setContractorId(3);
        ticket.setStatus("Contractor Assigned");

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
        String quoteToatlValue = quoteToatlEdit.getText().toString();
        if("".equals(quoteToatlValue.trim())){
            quoteToatlEdit.setError("Invalid Scope");
            return;
        }
        String finalPrice = finalPriceEdit.getText().toString();
        if("".equals(finalPrice.trim())){
            finalPriceEdit.setError("Invalid Name");
        }
        String varianceQuote = varianceQuoteEdit.getText().toString();
        if("".equals(varianceQuote.trim())){
            varianceQuoteEdit.setError("Invalid Note");
        }
        String quoteParts = quotePartsEdit.getText().toString();
        if("".equals(quoteParts.trim())){
            quotePartsEdit.setError("Invalid Note");
        }
        String quoteService = quoteServiceEdit.getText().toString();
        if("".equals(quoteService.trim())){
            quoteServiceEdit.setError("Invalid Note");
        }
        String quoteOthers = quoteOthersEdit.getText().toString();
        if("".equals(quoteOthers.trim())){
            quoteOthersEdit.setError("Invalid Note");
        }
        ContractorData contractorData = new ContractorData(GlobalFunctions.getCurrentDateTime(), Double.parseDouble(quoteToatlValue),Double.parseDouble(finalPrice),
                Double.parseDouble(varianceQuote), Double.parseDouble(quoteParts), Double.parseDouble(quoteService), Double.parseDouble(quoteOthers), userInfo);

        ticket.setContractorData(contractorData);
        ticket.setStatus("Contractor Quoted");

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/ticketing/" + ticket.getTicketNumber(), ticket.toMap());
        mDatabase.updateChildren(childUpdates);

        Intent intent = new Intent(this, ContractorMainActivity.class);
        intent.putExtra("UserInfo",userInfo);
        startActivity(intent);
    }

    private void setContractorQuoteInfo(){
        quotedDateView.setText(contractorData.getQuotedDateTime());
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
