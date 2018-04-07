package com.safe9.bfi.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.safe9.bfi.R;
import com.safe9.bfi.adapter.PatientAdapter;
import com.safe9.bfi.data.PatientColumns;
import com.safe9.bfi.model.Patient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.safe9.bfi.data.PatientProvider.Patients.URI_PATIENTS;


/**
 * Activity to select patient from a list of patients registered on the app locally
 */
public class SelectPatientActivity extends AppCompatActivity implements PatientAdapter.UserOnClickHandler{

    @BindView(R.id.rv_select_user)
    RecyclerView mRVSelectUser;
    @BindView(R.id.fab_add_user)
    FloatingActionButton mAddUserFAB;

    private PatientAdapter mPatientAdapter;
    private ArrayList<Patient> mPatientsList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_patient);
        ButterKnife.bind(this);

        setTitle(R.string.select_a_patient);

        mAddUserFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectPatientActivity.this, AddPatientActivity.class));
            }
        });

    }
    private void setupRecyclerView(){
        mPatientAdapter = new PatientAdapter(this, mPatientsList,this);

        mRVSelectUser.setLayoutManager(new GridLayoutManager(this,2));
        mRVSelectUser.setAdapter(mPatientAdapter);
        mRVSelectUser.setHasFixedSize(true);


    }

    private void loadUsers(){
        mPatientsList = new ArrayList<>();

        try {
            Cursor cursor = getContentResolver().query(URI_PATIENTS, null, null, null, null);
            if (cursor != null) {

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String userName = cursor.getString(cursor.getColumnIndexOrThrow(PatientColumns.NAME));
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(PatientColumns._ID));
                    String doctorName = cursor.getString(cursor.getColumnIndexOrThrow(PatientColumns.DOCTOR));
                    String aadhaar = cursor.getString(cursor.getColumnIndexOrThrow(PatientColumns.AADHAAR));
                    mPatientsList.add(new Patient(userName,id,doctorName,aadhaar));

                    cursor.moveToNext();
                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mPatientsList.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.no_patients_available)
                    .setMessage(R.string.please_create_patient)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            startActivity(new Intent(SelectPatientActivity.this,AddPatientActivity.class));
                            finish();
                        }
                    }).show();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        loadUsers();
        setupRecyclerView();


    }

    @Override
    public void onClick(Patient selectedPatient) {

        PreferenceManager.getDefaultSharedPreferences(SelectPatientActivity.this).edit().putInt(getString(R.string.SELECTED_USER_ID), selectedPatient.getmId()).apply();
        PreferenceManager.getDefaultSharedPreferences(SelectPatientActivity.this).edit().putString(getString(R.string.SELECTED_USER_NAME), selectedPatient.getName()).apply();

        Intent patientActivityIntent = new Intent(SelectPatientActivity.this,PatientActivity.class);
        patientActivityIntent.putExtra(Patient.PATIENT_CONSTANT, selectedPatient);
        startActivity(patientActivityIntent);

    }
}
