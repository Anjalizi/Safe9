package com.safe9.bfi.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.applandeo.materialcalendarview.CalendarView;
import com.safe9.bfi.R;
import com.safe9.bfi.model.Patient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VaccinationActivity extends AppCompatActivity {

    @BindView(R.id.calendar_view_vaccination)
    CalendarView mCalendarView;
    @BindView(R.id.fab_vaccine)
    FloatingActionButton mVaccineFAB;

    private Patient mPatient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination);

        ButterKnife.bind(this);
        mPatient = getIntent().getParcelableExtra(Patient.PATIENT_CONSTANT);
        setupCalendarView();
        mVaccineFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addVaccineRecord();
            }
        });

    }
    private void setupCalendarView(){
        mCalendarView.showCurrentMonthPage();

    }


    private void addVaccineRecord(){

    }
}

