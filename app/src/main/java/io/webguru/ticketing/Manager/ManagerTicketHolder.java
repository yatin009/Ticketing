package io.webguru.ticketing.Manager;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.webguru.ticketing.POJO.FieldAgentData;
import io.webguru.ticketing.POJO.ManagerData;
import io.webguru.ticketing.R;

/**
 * Created by yatin on 27/09/16.
 */

public class ManagerTicketHolder extends RecyclerView.ViewHolder {
    private View mView;

    public ManagerTicketHolder(View itemView) {
        super(itemView);
        mView = itemView;

    }

    public void setViewElements(ManagerData managerData) {
        setPriority(managerData.getPriority());
        setTicketStatus(managerData.getStatus(), managerData.getContractor());
        setTicketNumber(managerData.getTicketNumber());
        setProblem(managerData.getProblem());
        setLocation(managerData.getLocation());
        setDateTime(managerData.getRequestDateTime());
        setContractorName(managerData.getContractor());
        setAgentName(managerData.getAgentName());
    }

    private void setPriority(String priority) {
        TextView txtPriority = (TextView) mView.findViewById(R.id.txtPriority);
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
        ImageView ticketStatus = (ImageView) mView.findViewById(R.id.ticketStatus);
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
        TextView txtTicketNumber = (TextView) mView.findViewById(R.id.txtTicketNumber);
        if (ticketNumber == null) {
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

    private void setContractorName(String contractorName) {
        TextView txtContractorName = (TextView) mView.findViewById(R.id.txtContractorName);
        if (contractorName == null) {
            txtContractorName.setVisibility(View.GONE);
            return;
        }
        txtContractorName.setText(contractorName);
    }

    private void setAgentName(String agentName) {
        TextView txtAgentName = (TextView) mView.findViewById(R.id.txtAgentName);
        txtAgentName.setText(agentName);
    }
}