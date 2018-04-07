package com.safe9.bfi.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.marcoscg.fingerauth.FingerAuth;
import com.marcoscg.fingerauth.FingerAuthDialog;
import com.safe9.bfi.R;
import com.safe9.bfi.model.Patient;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Activity to show options for the patient including sonography
 */
public class PatientActivity extends AppCompatActivity {

    @BindView(R.id.iv_checkup)
    ImageView mGeneralCheckupImageView;
    @BindView(R.id.iv_test)
    ImageView mMedicalTestsImageView;
    @BindView(R.id.iv_sono)
    ImageView mSonoImageView;
    @BindView(R.id.iv_vaccine)
    ImageView mVaccineImageView;
    @BindView(R.id.fab_patient_add_child)
    FloatingActionButton mAddChildFAB;


    private Patient mPatient;
    public static final String TARGET_ACTIVITY = "target";


    private String KEY_NAME = "scan_fingerprint";
    private static final String CHECKUP_ACTIVITY_KEY = "General Checkup";
    private static final String SONO_ACTIVITY_KEY = "Sonography";
    private static final String MEDICAL_TEST_ACTIVITY_KEY = "Medical Test";
    private static final String VACCINATION_ACTIVITY_KEY = "Vaccination";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        ButterKnife.bind(this);

        mPatient = getIntent().getParcelableExtra(Patient.PATIENT_CONSTANT);

        mSonoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticateUser(SONO_ACTIVITY_KEY);
            }
        });
        mGeneralCheckupImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticateUser(CHECKUP_ACTIVITY_KEY);
            }
        });
        mMedicalTestsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticateUser(MEDICAL_TEST_ACTIVITY_KEY);
            }
        });
        mVaccineImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticateUser(VACCINATION_ACTIVITY_KEY);
            }
        });
        mAddChildFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addChildIntent =new Intent(PatientActivity.this,AddChildActivity.class);
                addChildIntent.putExtra(Patient.PATIENT_CONSTANT,mPatient);
                startActivity(addChildIntent);
            }
        });

    }

    private void authenticateUser(final String activityKey) {
        new FingerAuthDialog(this)
                .setTitle("Authenticate user")
                .setCancelable(false)
                .setMaxFailedCount(3) // Number of attempts, default 3
                .setPositiveButton("Use password", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // do something
                    }
                })
                .setOnFingerAuthListener(new FingerAuth.OnFingerAuthListener() {
                    @Override
                    public void onSuccess() {
                        Timber.d("Fingerprint onSuccess");
                        launchActivity(activityKey);
                    }

                    @Override
                    public void onFailure() {

                        Timber.d("Fingerprint onFailure");

                    }

                    @Override
                    public void onError() {

                        Timber.d("Fingerprint onError");
                    }
                })
                .show();
    }

    private void launchActivity(String targetActivity) {

        switch (targetActivity) {
            case SONO_ACTIVITY_KEY:
                Intent sonoIntent = new Intent(PatientActivity.this, SonographyActivity.class);
                sonoIntent.putExtra(Patient.PATIENT_CONSTANT, mPatient);
                startActivity(sonoIntent);
                break;
            case CHECKUP_ACTIVITY_KEY:
            case MEDICAL_TEST_ACTIVITY_KEY:
                Intent placeholderIntent = new Intent(PatientActivity.this, PlaceholderActivity.class);
                placeholderIntent.putExtra(Patient.PATIENT_CONSTANT, mPatient);
                placeholderIntent.putExtra(TARGET_ACTIVITY, targetActivity);
                startActivity(placeholderIntent);

                break;
            case VACCINATION_ACTIVITY_KEY:
                Intent vaccinationIntent = new Intent(PatientActivity.this, VaccinationActivity.class);
                vaccinationIntent.putExtra(Patient.PATIENT_CONSTANT, mPatient);
                startActivity(vaccinationIntent);

                break;
            default:
                break;
        }

    }


}
