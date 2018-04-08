package com.safe9.bfi.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.safe9.bfi.R;
import com.safe9.bfi.model.Patient;
import com.safe9.bfi.utils.QueryUtils;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.safe9.bfi.ui.LoginActivity.SHARED_PREFS_KEY;


/**
 * Activity to initiate sonography procedure after verification with 8 bit token
 *
 */
public class SonographyActivity extends AppCompatActivity {

    @BindView(R.id.tv_sono_doctor)
    TextView mDoctorTextView;
    @BindView(R.id.tv_sono_patient)
    TextView mPatientTextView;
    @BindView(R.id.tv_sono_radiologist)
    TextView mRadiologistTextView;
    @BindView(R.id.tv_sono_token)
    TextView mTokenTextView;

    private Patient mPatient;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonography);

        ButterKnife.bind(this);

        mPatient = getIntent().getParcelableExtra(Patient.PATIENT_CONSTANT);

        mPatientTextView.setText(mPatient.getName());
        mDoctorTextView.setText(mPatient.getmDoctor());
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        mRadiologistTextView.setText(prefs.getString(LoginActivity.RADIO_NAME,"Dr. Radha Krishna"));

        token= getRandomHexString(8);
        mTokenTextView.setText(token);


        RequestQueue queue = Volley.newRequestQueue(this);

        QueryUtils.sendTokenRequest(queue, false, token);


    }

    private String getRandomHexString(int numchars){
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < numchars){
            sb.append(r.nextInt(10));
        }

        return sb.toString().substring(0, numchars);
    }
}
