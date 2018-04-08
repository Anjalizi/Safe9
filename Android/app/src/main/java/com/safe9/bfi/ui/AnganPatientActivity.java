package com.safe9.bfi.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.safe9.bfi.R;
import com.safe9.bfi.model.AnganPatient;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity to display details of a particular patient for Anganwadi worker ;data retrieved from server
 */

public class AnganPatientActivity extends AppCompatActivity {


    @BindView(R.id.tv_angan_patient_name)
    TextView mNameTextView;
    @BindView(R.id.tv_angan_patient_weeks)
    TextView mWeeksTextView;
    @BindView(R.id.tv_angan_patient_vaccine)
    TextView mVaccineTextView;
    @BindView(R.id.btn_visit_patient)
    Button mVisitButton;

    private AnganPatient mPatient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angan_patient);

        ButterKnife.bind(this);

        mPatient = getIntent().getParcelableExtra(AnganPatient.ANGAN_PATIENT_CONSTANT);

        mNameTextView.setText(mPatient.getmName());
        if (mPatient.getmWeeks() == 0) {
            mWeeksTextView.setVisibility(View.GONE);
            mVaccineTextView.setVisibility(View.VISIBLE);
        } else {

            mVaccineTextView.setVisibility(View.GONE);
            mWeeksTextView.setText(String.valueOf(mPatient.getmWeeks()) + " weeks pregnant");
        }
        mVisitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr=" + mPatient.getmLatitude() + "," + mPatient.getmLongitude()));
                startActivity(intent);
            }
        });


    }
}
