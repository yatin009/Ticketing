package io.webguru.ticketing.Agent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.webguru.ticketing.R;

public class Contractor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractor);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new ContractorListFragment()).commit();
        }
    }
}
