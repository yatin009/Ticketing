package io.webguru.ticketing.Agent;

import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.webguru.ticketing.POJO.ManagerData;
import io.webguru.ticketing.POJO.Ticket;
import io.webguru.ticketing.R;

/**
 * Created by yatin on 27/09/16.
 */

public class AgentTicketHolder extends RecyclerView.ViewHolder {
    private View mView;
    private String TAG= "AGENTTICKETVIEWHOLDER";

    public AgentTicketHolder(View itemView) {
        super(itemView);
        mView = itemView;

    }

    public void setViewElements(Ticket ticket, int position, boolean isShow) {
        setPriority(ticket.getPriority());
        String contName = "";
        if(ticket.getAgentData()!=null && ticket.getAgentData().getContractor()!=null){
            contName = ticket.getAgentData().getContractor();
        }
        setTicketStatus(ticket.getStatus(), contName);
//        setContractorName(contName);
        setTicketNumber(ticket.getTicketNumber());
        setProblem(ticket.getRequester().getIssue());
        setLocation(ticket.getRequester().getLocation());
        setDateTime(ticket.getDateTime());
        setAssignContractorListner(ticket, position);
//        setAgentName(ticket.getRequester().getUserInfo().getFirstname());
    }

    private void setPriority(String priority) {
        TextView txtPriority = (TextView) mView.findViewById(R.id.ticket_priority);
        if ("HIGH".equals(priority)) {
            txtPriority.setTextColor(Color.RED);
        } else if ("MEDIUM".equals(priority)) {
            txtPriority.setTextColor(Color.rgb(255, 165, 0));//Orange
        } else if ("LOW".equals(priority)) {
            txtPriority.setTextColor(Color.BLACK);
        }
        txtPriority.setText(priority);
    }

    private void setTicketStatus(String status, String contractName) {
        ImageView ticketStatus = (ImageView) mView.findViewById(R.id.ticket_status_image);
        if ("pending".equals(status)) {
            ticketStatus.setImageResource(R.drawable.ticket_pending);
        } else if ("approved".equals(status)) {
            if(contractName!=null && !"".equals(contractName.trim())) {
                ticketStatus.setImageResource(R.drawable.ticket_assigned);
            } else {
                ticketStatus.setImageResource(R.drawable.ticket_approved);
            }
        } else if ("cancel".equals(status)) {
            ticketStatus.setImageResource(R.drawable.ticket_cancled);
        }
    }

    private void setTicketNumber(String ticketNumber) {
        TextView txtTicketNumber = (TextView) mView.findViewById(R.id.ticket_number);
        if (ticketNumber == null) {
            txtTicketNumber.setVisibility(View.GONE);
            return;
        }
        txtTicketNumber.setText(ticketNumber);
    }

    private void setProblem(String problem) {
        TextView txtProblem = (TextView) mView.findViewById(R.id.ticket_site);
        txtProblem.setText(problem);
    }

    private void setLocation(String problem) {
        TextView txtLocation = (TextView) mView.findViewById(R.id.ticket_location);
        txtLocation.setText(problem);
    }

    private void setDateTime(String dateTime) {
        TextView txtDateTime = (TextView) mView.findViewById(R.id.ticket_date);
        txtDateTime.setText(dateTime);
    }

    private void setContractorName(String contractorName) {
//        TextView txtContractorName = (TextView) mView.findViewById(R.id.txtContractorName);
//        if (contractorName == null) {
//            txtContractorName.setVisibility(View.GONE);
//            return;
//        }
//        txtContractorName.setText(contractorName);
    }

    private void setAgentName(String agentName) {
//        TextView txtAgentName = (TextView) mView.findViewById(R.id.txtAgentName);
//        txtAgentName.setText(agentName);
    }

    private void setAssignContractorListner(final Ticket ticket, final int position){
        TextView assignContractorLayout = (TextView) mView.findViewById(R.id.assign_contractor);
        assignContractorLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("AGENTTICKETVIEWHOLDER","position >>>>> "+position);
                Log.d("AGENTTICKETVIEWHOLDER","motionEvent >>>>> "+motionEvent.getAction());
                Log.d("AGENTTICKETVIEWHOLDER","motionEvent >>>>> "+MotionEvent.ACTION_DOWN);
                return false;
            }
        });
    }
}