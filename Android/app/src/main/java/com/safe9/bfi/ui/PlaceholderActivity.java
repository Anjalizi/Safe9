package com.safe9.bfi.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.safe9.bfi.R;
import com.safe9.bfi.model.Patient;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity to handle events other than sonography from the patient activity
 *
 */
public class PlaceholderActivity extends AppCompatActivity {

    @BindView(R.id.tv_placeholder_doctor)
    TextView mDoctorTextView;
    @BindView(R.id.tv_placeholder_patient)
    TextView mPatientTextView;
    @BindView(R.id.tv_placeholder_slip_label)
    TextView mPlaceholderTextView;
    @BindView(R.id.btn_proceed)
    Button mProceedButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeholder);

        ButterKnife.bind(this);
        Patient mPatient = getIntent().getParcelableExtra(Patient.PATIENT_CONSTANT);
        String label = getIntent().getStringExtra(PatientActivity.TARGET_ACTIVITY);
        mPlaceholderTextView.setText(label);
        mPatientTextView.setText(mPatient.getName());
        mDoctorTextView.setText(mPatient.getmDoctor());

        mProceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"To be Implemented in the near future",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
