package io.webguru.ticketing.Agent;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.webguru.ticketing.Approver.ApproverMainActivity;
import io.webguru.ticketing.Contractor.ContractorMainActivity;
import io.webguru.ticketing.Global.GlobalFunctions;
import io.webguru.ticketing.POJO.AgentData;
import io.webguru.ticketing.POJO.Analytics;
import io.webguru.ticketing.POJO.ApprovarData;
import io.webguru.ticketing.POJO.ContractorData;
import io.webguru.ticketing.POJO.RequesterData;
import io.webguru.ticketing.POJO.Ticket;
import io.webguru.ticketing.POJO.UserInfo;
import io.webguru.ticketing.R;

import static io.webguru.ticketing.Global.GlobalConstant.FILE_STORAGE_PATH;

public class AgentTicketView extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.progress_dialog)
    ProgressBar progressBar;
    @Bind(R.id.ticket_priority)
    TextView priorityView;
    @Bind(R.id.date_value)
    TextView dateView;

    @Bind(R.id.requester_name)
    TextView requesterNameView;
    @Bind(R.id.ticket_issue)
    TextView issueValue;
    @Bind(R.id.ticket_location)
    TextView locationView;
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
    @Bind(R.id.assign_contractor_button)
    AppCompatButton contractorButton;

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
    @Bind(R.id.quote_parts_value)
    TextView quotePartsView;
    @Bind(R.id.quote_others_value)
    TextView qupteOthersView;
    @Bind(R.id.call_contractor_button)
    ImageButton callContractor;
    @Bind(R.id.contractor_quote_date)
    TextView contractorQuoteDate;

    @Bind(R.id.internal_quote_layout)
    LinearLayout internalQuoteLayout;
    @Bind(R.id.approver_spinner)
    Spinner approverSpinner;

    @Bind(R.id.request_approval_view_layout)
    LinearLayout requestApprovalViewLayout;
    @Bind(R.id.request_approval_date)
    TextView requestApprovalDate;
    @Bind(R.id.approver_name_value)
    TextView approverNameValue;

    @Bind(R.id.approvar_layout)
    LinearLayout approvarLayout;
    @Bind(R.id.approver_signature)
    SignaturePad approverSignature;
    @Bind(R.id.approvar_note_edit)
    TextInputEditText approvarNoteEdit;

    @Bind(R.id.approval_view_layout)
    LinearLayout approvalViewLayout;
    @Bind(R.id.approver_signature_image)
    ImageView approverSignatureImage;
    @Bind(R.id.approver_note_value)
    TextView approverNoteValue;
    @Bind(R.id.approval_date)
    TextView approvalDate;
    ArrayAdapter<String> contractorAdapter, employeeAdapter;
    ProgressDialog progressDialog;
    Analytics analytics;
    private Ticket ticket;
    private RequesterData requesterData;
    private UserInfo userInfo;
    private AgentData agentData;
    private ContractorData contractorData;
    private ApprovarData approvarData;
    private String userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_ticket_view);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userInfo = (UserInfo) bundle.get("UserInfo");
            ticket = (Ticket) bundle.get("Ticket");
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Ticket# " + ticket.getTicketNumber());
        }
        userInfo = GlobalFunctions.getUserInfo(this);
        userRole = userInfo.getRole();
        requesterData = ticket.getRequester();
        setViews();
        spinnerValues();
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(FILE_STORAGE_PATH).child("issue_image/" + ticket.getTicketNumber() + ".jpg");
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
                Log.d("TICKETVIEW", "switch >>> " + checked);
                if (checked) {
                    scopeEditValue.setText("In");
                    contractorEmployeeListLabel.setText("Select a Employee");
                    contractorNameEditView.setAdapter(employeeAdapter);
                    contractorButton.setText("Assign Employee");
                } else {
                    scopeEditValue.setText("Out");
                    contractorEmployeeListLabel.setText("Select a Contractor");
                    contractorNameEditView.setAdapter(contractorAdapter);
                    contractorButton.setText("Assign Contractor");
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
                String labourValue = quoteLabourEdit.getText().toString();
                if ("".equals(labourValue)) {
                    quoteLabourEdit.setError("Please enter a value");
                    return;
                }
                String partsValue = quotePartsEdit.getText().toString();
                if ("".equals(partsValue)) {
                    quotePartsEdit.setError("Please enter a value");
                    return;
                }
                double sum = Double.parseDouble(labourValue) + Double.parseDouble(partsValue);
                String taxAmount = round(getTaxAmount(sum, Double.parseDouble("13"))) + "";
                quoteTaxEdit.setText(taxAmount);
                sum = round(sum + Double.parseDouble(taxAmount));
                totalTextValue.setText("$ " + sum);
            }
        };
        quoteLabourEdit.addTextChangedListener(textWatcher);
        quotePartsEdit.addTextChangedListener(textWatcher);
    }

    public double getTaxAmount(double total, double tax) {
        return (total / 100) * tax;
    }

    public double round(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private void setViews() {
        if ("HIGH".equals(ticket.getPriority())) {
            priorityView.setTextColor(Color.RED);
        } else if ("MEDIUM".equals(ticket.getPriority())) {
            priorityView.setTextColor(Color.rgb(255, 165, 0));//Orange
        } else if ("LOW".equals(ticket.getPriority())) {
            priorityView.setTextColor(Color.BLACK);
        }
        priorityView.setText(ticket.getPriority());
        dateView.setText(ticket.getDateTime());

        requesterNameView.setText(requesterData.getUserInfo().getFirstname());
        issueValue.setText(requesterData.getIssue());
        locationView.setText(requesterData.getLocation());

        if ("manager".equals(userRole)) {
            if (ticket.getAgentData() != null) {
                assignedContractorLayout.setVisibility(View.VISIBLE);
                agentData = ticket.getAgentData();
                setAssignedContractorInfo();
            } else {
                assignContractorLayout.setVisibility(View.VISIBLE);
            }
            if (ticket.getContractorData() != null) {
                contractorData = ticket.getContractorData();
                contractorQuoteViewLayout.setVisibility(View.VISIBLE);
                setContractorQuoteInfo();
                if ("Pending Approval".equals(ticket.getStatus())) {
                    internalQuoteLayout.setVisibility(View.VISIBLE);
                } else if ("Approver Assigned".equals(ticket.getStatus())) {
                    requestApprovalViewLayout.setVisibility(View.VISIBLE);
                    setRequestApproverView();
                }
                if ("Approved".equals(ticket.getStatus())) {
                    requestApprovalViewLayout.setVisibility(View.VISIBLE);
                    setRequestApproverView();
                    approvarData = ticket.getApprovarData();
                    approvalViewLayout.setVisibility(View.VISIBLE);
                    setApproverViews();
                }
            }
        } else if ("contractor".equals(userRole)) {
            if (ticket.getContractorData() == null) {
                contractorQuoteLayout.setVisibility(View.VISIBLE);
            } else if (ticket.getContractorData() != null) {
                contractorData = ticket.getContractorData();
                contractorQuoteViewLayout.setVisibility(View.VISIBLE);
                callContractor.setVisibility(View.GONE);
                setContractorQuoteInfo();
            }
        } else if ("approver".equals(userRole)) {
            //Agent Created Data
            assignedContractorLayout.setVisibility(View.VISIBLE);
            agentData = ticket.getAgentData();
            setAssignedContractorInfo();
            //Contractor Created Data
            contractorData = ticket.getContractorData();
            contractorQuoteViewLayout.setVisibility(View.VISIBLE);
            setContractorQuoteInfo();

            if ("Approver Assigned".equals(ticket.getStatus())) {
                approvarLayout.setVisibility(View.VISIBLE);
            } else {
                approvarData = ticket.getApprovarData();
                approvalViewLayout.setVisibility(View.VISIBLE);
                setApproverViews();
            }
        }
    }

    private void setAssignedContractorInfo() {
        assignedDateView.setText(agentData.getContractorAssignedDate());
        scopeView.setText(agentData.getScope());
        contractorView.setText(agentData.getContractor());
        contractorNoteView.setText(agentData.getContractorNote());
    }

    private void setContractorQuoteInfo() {
        contractorQuoteDate.setText(contractorData.getQuotedDateTime());
        quotePriceView.setText("$ " + contractorData.getQuotePriceTotal());
        quotePartsView.setText("$ " + contractorData.getQuotedParts());
        qupteOthersView.setText("$ " + contractorData.getQuotedLabour());
    }

    private void setRequestApproverView() {
        requestApprovalDate.setText(agentData.getRequestApprovalDate());
        approverNameValue.setText(agentData.getApproverName());
    }

    private void setApproverViews() {
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(FILE_STORAGE_PATH).child("signatures/" + approvarData.getSignatureImage());
        storageRef.getDownloadUrl().addOnSuccessListener(this, new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(AgentTicketView.this).load(uri).into(approverSignatureImage);
            }
        });
        approverNoteValue.setText(approvarData.getNote());
        approvalDate.setText(approvarData.getApprovedDateTime());
    }

    private void spinnerValues() {
        String[] contractorSpinner = new String[]{
                "Contractor 1", "Contrctor 2", "Contrctor 3", "Contrctor 4", "Contrctor 5"
        };
        contractorAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, contractorSpinner);

        String[] employeeSpinner = new String[]{
                "Employee 1", "Employee 2", "Employee 3", "Employee 4", "Employee 5"
        };
        employeeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, employeeSpinner);
    }

    @OnClick(R.id.ticket_proof_image)
    public void onImageClick() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = getLayoutInflater().inflate(R.layout.dialog_image, null);
        dialog.setContentView(view);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress_dialog);
        final ImageView imageView = (ImageView) view.findViewById(R.id.large_image);
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(FILE_STORAGE_PATH).child("issue_image" + ticket.getTicketNumber() + ".jpg");
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
    public void callContractorButtonAction() {
        String requesterNumber = contractorData.getUserInfo().getNumber();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + requesterNumber));
        startActivity(intent);
    }

    @OnClick(R.id.call_contractor)
    public void callContractor() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:4164797440"));
        startActivity(intent);
    }

    @OnClick(R.id.call_approver)
    public void callApprover() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:4164797440"));
        startActivity(intent);
    }

    @OnClick(R.id.call_approver_button)
    public void callApproverButton() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:4164797440"));
        startActivity(intent);
    }

    @OnClick(R.id.clear_signature)
    public void clearSignature() {
        approverSignature.clear();
    }

    @OnClick(R.id.freeze_sign)
    public void freezeSign() {
        if (approverSignature.isEnabled()) {
            approverSignature.setEnabled(false);
        } else {
            approverSignature.setEnabled(true);
        }
    }

    @OnClick(R.id.call_agent_button)
    public void callAgentButton() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:4164797440"));
        startActivity(intent);
    }

    //Status Update actions
    @OnClick(R.id.assign_contractor_button)
    public void assignContractorButtonAction() {
        String contractorName = contractorNameEditView.getSelectedItem().toString();
        if ("".equals(contractorName.trim())) {
            Toast.makeText(this, "Please Select a value from spinner", Toast.LENGTH_LONG);
            return;
        }
        String contractorNote = contractorNoteEditView.getText().toString();
        if ("".equals(contractorNote.trim())) {
            contractorNoteEditView.setError("Invalid Note");
            return;
        }
        String scopeValue = scopeEditValue.getText().toString();
        AgentData agentData = new AgentData(scopeValue, contractorName, 3, contractorNote,
                GlobalFunctions.getCurrentDateTime(), userInfo);
        ticket.setAgentData(agentData);
        ticket.setContractorId(3);
        ticket.setStatus("Contractor Assigned");
        ticket.setAgent_status("1_ContractorAssigned");
        ticket.setIsVisibleContractor("Yes");

        updateAnalytics(ticket.getStatus());
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/ticketing/" + ticket.getTicketNumber(), ticket.toMap());
        mDatabase.updateChildren(childUpdates);

        Intent intent = new Intent(this, AgentMainActivity.class);
        intent.putExtra("UserInfo", userInfo);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.quote_button)
    public void quoteByContractorAction() {

        ContractorData contractorData = new ContractorData(GlobalFunctions.getCurrentDateTime(), Double.parseDouble(totalTextValue.getText().toString().split(" ")[1]), Double.parseDouble("0"),
                Double.parseDouble("0"), Double.parseDouble(quotePartsEdit.getText().toString()), Double.parseDouble("0"), Double.parseDouble(quoteLabourEdit.getText().toString()), userInfo);

        ticket.setContractorData(contractorData);
        ticket.setStatus("Pending Approval");
        ticket.setAgent_status("1_PendingApproval");

        updateAnalytics(ticket.getStatus());
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/ticketing/" + ticket.getTicketNumber(), ticket.toMap());
        mDatabase.updateChildren(childUpdates);

        Intent intent = new Intent(this, ContractorMainActivity.class);
        intent.putExtra("UserInfo", userInfo);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.request_approval)
    public void requestApproval() {
        agentData.setApproverId(4);
        agentData.setApproverName(approverSpinner.getSelectedItem().toString());
        agentData.setRequestApprovalDate(GlobalFunctions.getCurrentDateTime());

        ticket.setAgentData(agentData);
        ticket.setStatus("Approver Assigned");
        ticket.setAgent_status("1_PendingApproval");

//        ApprovarData approvarData = new ApprovarData(userInfo, GlobalFunctions.getCurrentDateTime(), null, null);
//        ticket.setApprovarData(approvarData);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/ticketing/" + ticket.getTicketNumber(), ticket.toMap());
        mDatabase.updateChildren(childUpdates);

        Intent intent = new Intent(this, AgentMainActivity.class);
        intent.putExtra("UserInfo", userInfo);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.deny_ticket)
    public void denyTicket() {
        GlobalFunctions.showToast(this, "Ticket denied, functionality still to build", Toast.LENGTH_LONG);
    }

    @OnClick(R.id.accept_ticket)
    public void acceptTicket() {
        ApprovarData approvarData = new ApprovarData(userInfo, GlobalFunctions.getCurrentDateTime(), "sign_" + ticket.getTicketNumber() + ".jpg", approvarNoteEdit.getText().toString());
        ticket.setApprovarData(approvarData);
        ticket.setStatus("Approved");
        ticket.setAgent_status("1_Approved");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(FILE_STORAGE_PATH);

        StorageReference signImageRef = storageRef.child("signatures/sign_" + ticket.getTicketNumber() + ".jpg");

        Bitmap bitmap = approverSignature.getSignatureBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        progressDialog = new ProgressDialog(this);
        progressDialog.show(this, "Genrating Ticket", "Please wait", false, false);
        UploadTask uploadTask = signImageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                updateAnalytics(ticket.getStatus());
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/ticketing/" + ticket.getTicketNumber(), ticket.toMap());
                mDatabase.updateChildren(childUpdates);

                Intent intent = new Intent(AgentTicketView.this, ApproverMainActivity.class);
                intent.putExtra("UserInfo", userInfo);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void updateAnalytics(final String increaseStatus) {
        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("analytics").child(GlobalFunctions.getTodaysDateAsFireBaseKey());
        mDatabase1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot != null && snapshot.getValue() != null) {
                    analytics = snapshot.getValue(Analytics.class);
                } else {
                    System.out.println("No data exists for analyitcs on " + GlobalFunctions.getTodaysDateFormatted());
                }
                if ("Contractor Assigned".equals(increaseStatus)) {
                    analytics.setDispatchedCount(analytics.getDispatchedCount() + 1);
                    analytics.setIncomingCount(analytics.getIncomingCount() - 1);
                } else if ("Pending Approval".equals(increaseStatus)) {
                    analytics.setApprovalCount(analytics.getApprovalCount() + 1);
                    analytics.setDispatchedCount(analytics.getDispatchedCount() - 1);
                } else if ("Approved".equals(increaseStatus)) {
                    analytics.setApprovedCount(analytics.getApprovedCount() + 1);
                    analytics.setApprovalCount(analytics.getApprovalCount() - 1);
                }
                DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference();
                Map<String, Object> childUpdates1 = new HashMap<>();
                childUpdates1.put("/analytics/" + GlobalFunctions.getTodaysDateAsFireBaseKey(), analytics);
                mDatabase1.updateChildren(childUpdates1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
