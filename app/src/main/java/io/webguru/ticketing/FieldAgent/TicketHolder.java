package io.webguru.ticketing.FieldAgent;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.webguru.ticketing.Global.GlobalFunctions;
import io.webguru.ticketing.POJO.FieldAgentData;
import io.webguru.ticketing.POJO.UserInfo;
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
        setTicketStatus(fieldAgentData.getStatus());
        setTicketNumber(fieldAgentData.getTicketNumber());
        setProblem(fieldAgentData.getDescription());
        setLocation(fieldAgentData.getLocation());
        setDateTime(fieldAgentData.getDateTime());

        setSite(fieldAgentData.getSite());
        setShop(fieldAgentData.getShop());
        setScope(fieldAgentData.getScope());
    }

    private void setPriority(String priority) {
        TextView txtPriority = (TextView) mView.findViewById(R.id.ticket_priority);
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
//        Log.d("TICKETHOLDER","dateTime >>> "+dateTime);
//        SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy HH:mm aaa", Locale.US);
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
//        String newDateTime = null;
//        try {
//            newDateTime = sdf1.format(sdf.parse(dateTime));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
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
        TextView txtSite = (TextView) mView.findViewById(R.id.ticket_site);
        txtSite.setText(site);
    }

    private void setShop(String shop){
//        TextView txtShop = (TextView) mView.findViewById(R.id.txtShop);
//        txtShop.setText(shop);
    }

    private void setScope(String scope){
//        TextView txtScope = (TextView) mView.findViewById(R.id.ticket_scope);
//        txtScope.setText(scope);
    }

}
