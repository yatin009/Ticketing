package io.webguru.ticketing.Requester;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.webguru.ticketing.Global.GlobalFunctions;
import io.webguru.ticketing.POJO.FieldAgentData;
import io.webguru.ticketing.POJO.Ticket;
import io.webguru.ticketing.R;

/**
 * Created by yatin on 25/09/16.
 */

public class RequesterTicketHolder extends RecyclerView.ViewHolder {
    private View mView;

    public RequesterTicketHolder(View itemView) {
        super(itemView);
        mView = itemView;

    }

    public void setViewElements(Ticket ticket, boolean toShow){
        if(!toShow){
            mView.setVisibility(View.GONE);
            return;
        }
        setPriority(ticket.getPriority());
        setTicketStatus(ticket.getStatus());
        setTicketNumber(ticket.getTicketNumber());
        setProblem(ticket.getRequester().getIssue());
        setLocation(ticket.getRequester().getLocation());
        setDateTime(ticket.getDateTime());

        setSite(ticket.getRequester().getSite());
        setShop(ticket.getRequester().getShop());

        setDetailsView(ticket.getRequester().getShop(), ticket.getRequester().getIssue());
    }

    private void setPriority(String priority) {
        TextView txtPriority = (TextView) mView.findViewById(R.id.ticket_priority);
        ImageView priorityIndicator = (ImageView) mView.findViewById(R.id.title_from_to_dots);
        if( "HIGH".equals(priority)){
            txtPriority.setTextColor(Color.RED);
            priorityIndicator.setImageResource(R.color.cpb_red);
        }else if ("MEDIUM".equals(priority)){
            txtPriority.setTextColor(Color.rgb(255,165,0));//Orange
            priorityIndicator.setImageResource(R.color.orange);
        }else if("LOW".equals(priority)){
            txtPriority.setTextColor(Color.BLACK);
            priorityIndicator.setImageResource(R.color.cpb_grey);
        }
        txtPriority.setText(priority);
    }

    private void setTicketStatus(String status) {
        ImageView ticketStatus = (ImageView) mView.findViewById(R.id.ticket_status_image);
        if( "pending".equals(status)){
            ticketStatus.setImageResource(R.drawable.ticket_pending);
        }else if ("approved".equals(status)){
            ticketStatus.setImageResource(R.drawable.ticket_approved);
        }else if("canceled".equals(status)){
            ticketStatus.setImageResource(R.drawable.ticket_cancled);
        }
    }

    private void setTicketNumber(String ticketNumber) {
        TextView txtTicketNumber = (TextView) mView.findViewById(R.id.ticket_number);
        if(ticketNumber==null || "".equals(ticketNumber)){
            txtTicketNumber.setText("Ticket number not generated");
            return;
        }
        txtTicketNumber.setText(ticketNumber);
    }

    private void setProblem(String problem) {
//        TextView txtProblem = (TextView) mView.findViewById(R.id.txtProblem);
//        txtProblem.setText(problem);
    }

    private void setLocation(String problem) {
        TextView txtLocation = (TextView) mView.findViewById(R.id.ticket_location);
        txtLocation.setText(problem);
    }

    private void setDateTime(String dateTime) {
        Log.d("TICKETHOLDER","dateTime >>> "+dateTime);
        String[] dateTimeArray = dateTime.split(" ");
        TextView txtDate = (TextView) mView.findViewById(R.id.ticket_date);
        txtDate.setText(getDateDiff(dateTimeArray[0]));
        TextView txtTime = (TextView) mView.findViewById(R.id.ticket_time);
        txtTime.setText(dateTimeArray[1]+" "+dateTimeArray[2]);
    }

    public String getDateDiff(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            Date currDate = sdf.parse(GlobalFunctions.getTodaysDateFormatted());
            Date fileDate = sdf.parse(date);
            long diff = currDate.getTime() - fileDate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if(diffDays==0) {
                return "Today";
            }else{
                return diffDays + " days ago";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Today";
        }
    }

    private void setSite(String site){
//        TextView txtSite = (TextView) mView.findViewById(R.id.ticket_site);
//        txtSite.setText(site);
    }

    private void setShop(String shop){
//        TextView txtShop = (TextView) mView.findViewById(R.id.txtShop);
//        txtShop.setText(shop);
    }

    private void setScope(String scope){
//        TextView txtScope = (TextView) mView.findViewById(R.id.ticket_scope);
//        txtScope.setText(scope);
    }

    private void setDetailsView(String shopText, String issueText){
        TextView shop = (TextView) mView.findViewById(R.id.shop_value);
        TextView issue = (TextView) mView.findViewById(R.id.issue_value);
        shop.setText(shopText);
        issue.setText(issueText);
    }

}
