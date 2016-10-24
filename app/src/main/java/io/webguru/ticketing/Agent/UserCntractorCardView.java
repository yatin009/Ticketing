package io.webguru.ticketing.Agent;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.webguru.ticketing.POJO.UserContractor;
import io.webguru.ticketing.R;

/**
 * Created by yatin on 23/10/16.
 */

public class UserCntractorCardView extends RecyclerView.ViewHolder {
    @Bind(R.id.contractor_company)
    TextView contractorCompany;
    @Bind(R.id.contractor_name)
    TextView contractorName;
    @Bind(R.id.contractor_email)
    TextView contractorEmail;
    @Bind(R.id.call_contractor_button)
    ImageButton callContractor;

    private View mView;

    public UserCntractorCardView(View itemView) {
        super(itemView);
        this.mView = itemView;
        ButterKnife.bind(this, mView);
    }

    public void setViewElements(UserContractor userContractor){
        setCompanyName(userContractor.getCompanyName());
    }

    private void setCompanyName(String cmpnyName){
        contractorCompany.setText(cmpnyName);
    }

    private void setName(String name){
        contractorName.setText(name);
    }

    private void setEmail(String email){
        contractorEmail.setText(email);
    }

}
