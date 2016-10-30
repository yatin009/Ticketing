package io.webguru.ticketing.Agent;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.webguru.ticketing.Global.GlobalFunctions;
import io.webguru.ticketing.POJO.Ticket;
import io.webguru.ticketing.R;

/**
 * Created by yatin on 27/09/16.
 */

public class AgentTicketHolder extends RecyclerView.ViewHolder {
    private View mView;
    private String TAG = "AGENTTICKETVIEWHOLDER";

    public AgentTicketHolder(View itemView) {
        super(itemView);
        mView = itemView;

    }

    public void setViewElements(Ticket ticket, int position, boolean isShow) {
        setPriority(ticket.getPriority());
        setTicketNumber(ticket.getTicketNumber());
        setProblem(ticket.getRequester().getIssue());
        setLocation(ticket.getRequester().getLocation());
        setDateTime(ticket.getDateTime());
        setAssignContractorListner(ticket, position);
        if (ticket.getContractorData() != null) {
            setTicketFinalCost(ticket.getContractorData().getQuotePriceTotal());
        } else {
            LinearLayout linearLayout = (LinearLayout) mView.findViewById(R.id.quote_cost_layout);
            linearLayout.setVisibility(View.GONE);
        }
    }

    private void setPriority(String priority) {
        TextView txtPriority = (TextView) mView.findViewById(R.id.ticket_priority);
        ImageView priorityIndicator = (ImageView) mView.findViewById(R.id.title_from_to_dots);
        if ("HIGH".equals(priority)) {
            txtPriority.setTextColor(Color.RED);
            priorityIndicator.setImageResource(R.color.cpb_red);
        } else if ("MEDIUM".equals(priority)) {
            txtPriority.setTextColor(Color.rgb(255, 165, 0));//Orange
            priorityIndicator.setImageResource(R.color.orange);
        } else if ("LOW".equals(priority)) {
            txtPriority.setTextColor(Color.BLACK);
            priorityIndicator.setImageResource(R.color.cpb_grey);
        }
        txtPriority.setText(priority);
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
        Log.d("TICKETHOLDER", "dateTime >>> " + dateTime);
        String[] dateTimeArray = dateTime.split(" ");
        TextView txtDate = (TextView) mView.findViewById(R.id.ticket_date);
        txtDate.setText(getDateDiff(dateTimeArray[0]));
        TextView txtTime = (TextView) mView.findViewById(R.id.ticket_time);
        txtTime.setText(dateTimeArray[1] + " " + dateTimeArray[2]);
    }

    public String getDateDiff(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            Date currDate = sdf.parse(GlobalFunctions.getTodaysDateFormatted());
            Date fileDate = sdf.parse(date);
            long diff = currDate.getTime() - fileDate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if (diffDays == 0) {
                return "Today";
            } else {
                return diffDays + " days ago";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Today";
        }
    }

    private void setTicketFinalCost(double finalPrice) {
        LinearLayout linearLayout = (LinearLayout) mView.findViewById(R.id.quote_cost_layout);
        linearLayout.setVisibility(View.VISIBLE);
        TextView costView = (TextView) mView.findViewById(R.id.final_cost);
        costView.setText("$" + finalPrice);
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

    private void setAssignContractorListner(final Ticket ticket, final int position) {
        TextView assignContractorLayout = (TextView) mView.findViewById(R.id.assign_contractor);
        assignContractorLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("AGENTTICKETVIEWHOLDER", "position >>>>> " + position);
                Log.d("AGENTTICKETVIEWHOLDER", "motionEvent >>>>> " + motionEvent.getAction());
                Log.d("AGENTTICKETVIEWHOLDER", "motionEvent >>>>> " + MotionEvent.ACTION_DOWN);
                return false;
            }
        });
    }
}