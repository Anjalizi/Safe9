package com.safe9.bfi.ui;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.safe9.bfi.R;
import com.safe9.bfi.model.Patient;
import com.safe9.bfi.utils.QueryUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class AddChildActivity extends AppCompatActivity {

    @BindView(R.id.btn_add_child_submit)
    Button mAddChildSubmitButton;
    @BindView(R.id.pb_add_child_indicator)
    ProgressBar mLoadingIndicator;
    @BindView(R.id.spinner_add_child_gender)
    Spinner mGenderSpinner;
    @BindView(R.id.spinner_add_child_method)
    Spinner mMethodSpinner;
    @BindView(R.id.et_birthmark)
    EditText mBirthmarkEditText;
    @BindView(R.id.et_child_weight)
    EditText mWeightEditText;


    private FusedLocationProviderClient mFusedLocationClient;

    private Patient mPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        setTitle(R.string.add_child_title);

        ButterKnife.bind(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        mPatient = getIntent().getParcelableExtra(Patient.PATIENT_CONSTANT);

        mAddChildSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mLoadingIndicator.setVisibility(View.VISIBLE);
                getLocation();
            }
        });


    }

    private void submitChildDetails(Location location) {
        String gender = (String) mGenderSpinner.getSelectedItem();
        String method = (String) mMethodSpinner.getSelectedItem();
        String birthmark = mBirthmarkEditText.getText().toString().trim();
        String aadhaar = mPatient.getmAadhaar();
        String weight = mWeightEditText.getText().toString().trim();

        double mLatitude = location.getLatitude();
        double mLongitude = location.getLongitude();


        String urlParams =
                "?gender=" + gender
                        + "&birth_method=" + method
                        + "&birth_mark=" + birthmark
                        + "&lat=" + mLatitude
                        + "&long=" + mLongitude
                        + "&weight=" + weight
                        + "&mother_aadhar=" + aadhaar;
        Timber.d("urlParams "+urlParams);

        RequestQueue queue = Volley.newRequestQueue(this);
        QueryUtils.sendPatientDataRequest(queue, false, false,urlParams);

        mLoadingIndicator.setVisibility(View.GONE);
        Toast.makeText(this, "Child details successfully registered", Toast.LENGTH_SHORT).show();
        finish();

    }

    private void getLocation() throws SecurityException {

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            submitChildDetails(location);
                        }
                    }
                });
    }

}
