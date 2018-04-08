package com.safe9.bfi.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.safe9.bfi.R;
import com.safe9.bfi.data.PatientColumns;
import com.safe9.bfi.data.PatientProvider;
import com.safe9.bfi.utils.QueryUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


/**
 * Activity to be displayed to add a new patient to the local database and to the server database
 * (server database contains data from all such Android device databases)
 */
public class AddPatientActivity extends AppCompatActivity implements QueryUtils.QueryCallback{


    @BindView(R.id.et_phone)
    EditText mPhoneEditText;
    @BindView(R.id.et_user_name)
    EditText mNameEditText;
    @BindView(R.id.et_number_weeks)
    EditText mWeeksEditText;
    @BindView(R.id.btn_add_user)
    Button mAddUserButton;
    @BindView(R.id.et_age)
    EditText mAgeEditText;
    @BindView(R.id.et_weight)
    EditText mWeightEditText;
    @BindView(R.id.et_address)
    EditText mAddressEditText;
    @BindView(R.id.et_aadhaar)
    EditText mAadhaarEditText;
    @BindView(R.id.et_doctor)
    EditText mDoctorEditText;

    private static final String BASE_URL = "https://finalapo.herokuapp.com/";
    private static String urlParams;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        ButterKnife.bind(this);
        setTitle(R.string.create_a_patient);
        mAddUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertUserInDb();
            }
        });
    }

    private void insertUserInDb() {

        String name = mNameEditText.getText().toString().trim();
        String phone = mPhoneEditText.getText().toString();
        int weeks = Integer.valueOf(mWeeksEditText.getText().toString().trim());
        int age = Integer.valueOf(mAgeEditText.getText().toString().trim());
        int weight = Integer.valueOf(mWeightEditText.getText().toString().trim());
        String address = mAddressEditText.getText().toString().trim();
        String aadhaar = mAadhaarEditText.getText().toString().trim();
        String doctor = mDoctorEditText.getText().toString().trim();

        ContentValues values = new ContentValues();
        values.put(PatientColumns.NAME, name);
        values.put(PatientColumns.PHONE, phone);
        values.put(PatientColumns.WEEKS, weeks);
        values.put(PatientColumns.AGE, age);
        values.put(PatientColumns.WEIGHT, weight);
        values.put(PatientColumns.ADDRESS, address);
        values.put(PatientColumns.AADHAAR, aadhaar);
        values.put(PatientColumns.DOCTOR, doctor);
        Uri insertUri = getContentResolver().insert(PatientProvider.Patients.URI_PATIENTS, values);
        int pid = Integer.valueOf(insertUri.getLastPathSegment());

        QueryUtils.getLatLongFromAddress(Volley.newRequestQueue(this),address,this);
        urlParams =
                "?pid=" + pid
                        + "&name=" + name
                        + "&phone=" + phone
                        + "&weeks=" + weeks
                        + "&age=" + age
                        + "&weight=" + weight
                        + "&address=" + address
                        + "&aadhar=" + aadhaar;
    }

    @Override
    public void returnLatLng(double lat, double lng) {

        urlParams += "&latitude="+lat+"&longitude="+lng;
        Timber.d("urlParams: " + urlParams);
        RequestQueue queue = Volley.newRequestQueue(this);
        QueryUtils.sendPatientDataRequest(queue, false,true, urlParams);
        startActivity(new Intent(AddPatientActivity.this, SelectPatientActivity.class));
        finish();

    }

    @Override
    public void returnResponse(String response) {

    }
}

