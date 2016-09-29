package io.webguru.ticketing.FieldAgent;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import io.webguru.ticketing.POJO.FieldAgentData;
import io.webguru.ticketing.R;

/**
 * Created by yatin on 25/09/16.
 */

public class TicketHolder extends RecyclerView.ViewHolder {
    private View mView;

    public TicketHolder(View itemView) {
        super(itemView);
        mView = itemView;

    }

    public void setViewElements(FieldAgentData fieldAgentData){
        setPriority(fieldAgentData.getPriority());
        setTicketStatus(fieldAgentData.getApproved());
        setTicketNumber(fieldAgentData.getTicketNumber());
        setProblem(fieldAgentData.getProblem());
        setLocation(fieldAgentData.getLocation());
        setDateTime(fieldAgentData.getDateTime());
    }

    private void setPriority(String priority) {
        TextView txtPriority = (TextView) mView.findViewById(R.id.txtPriority);
        if( "HIGH".equals(priority)){
            txtPriority.setTextColor(Color.RED);
        }else if ("MEDIUM".equals(priority)){
            txtPriority.setTextColor(Color.rgb(255,165,0));//Orange
        }else if("LOW".equals(priority)){
            txtPriority.setTextColor(Color.BLACK);
        }
        txtPriority.setText(priority);
    }

    private void setTicketStatus(String status) {
        ImageView ticketStatus = (ImageView) mView.findViewById(R.id.ticketStatus);
        if( "pending".equals(status)){
            ticketStatus.setImageResource(R.drawable.ticket_pending);
        }else if ("approved".equals(status)){
            ticketStatus.setImageResource(R.drawable.ticket_approved);
        }else if("canceled".equals(status)){
            ticketStatus.setImageResource(R.drawable.ticket_cancled);
        }
    }

    private void setTicketNumber(String ticketNumber) {
        TextView txtTicketNumber = (TextView) mView.findViewById(R.id.txtTicketNumber);
        if(ticketNumber==null){
            txtTicketNumber.setVisibility(View.GONE);
            return;
        }
        txtTicketNumber.setText(ticketNumber);
    }

    private void setProblem(String problem) {
        TextView txtProblem = (TextView) mView.findViewById(R.id.txtProblem);
        txtProblem.setText(problem);
    }

    private void setLocation(String problem) {
        TextView txtLocation = (TextView) mView.findViewById(R.id.txtLocation);
        txtLocation.setText(problem);
    }

    private void setDateTime(String dateTime) {
        TextView txtDateTime = (TextView) mView.findViewById(R.id.txtDateTime);
        txtDateTime.setText(dateTime);
    }

}
