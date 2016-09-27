package io.webguru.ticketing.FieldAgent;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import io.webguru.ticketing.R;

/**
 * Created by yatin on 25/09/16.
 */

public class TicketHolder extends RecyclerView.ViewHolder {
    View mView;
//    private TextView vSurname;
//    private TextView vEmail;
//    private TextView vTitle;
//    private int holderId;

    public TicketHolder(View itemView) {
        super(itemView);
        mView = itemView;

    }

    public void setPriority(String priority) {
        TextView txtPriority = (TextView) mView.findViewById(R.id.txtPriority);
        txtPriority.setText(priority);
    }

    public void setProblem(String problem) {
        TextView txtProblem = (TextView) mView.findViewById(R.id.txtProblem);
        txtProblem.setText(problem);
    }

    public void setDate(String date) {
        TextView txtDate = (TextView) mView.findViewById(R.id.txtDate);
        txtDate.setText(date);
    }

    public void setTime(String time) {
        TextView txtTime = (TextView) mView.findViewById(R.id.txtTime);
        txtTime.setText(time);
    }
}
