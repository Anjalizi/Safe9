package com.safe9.bfi.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.safe9.bfi.R;
import com.safe9.bfi.data.ChildColumns;
import com.safe9.bfi.model.Child;
import com.safe9.bfi.model.Patient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.safe9.bfi.data.PatientProvider.Children.URI_CHILDREN;

public class VaccinationActivity extends AppCompatActivity {

    @BindView(R.id.calendar_view_vaccination)
    CalendarView mCalendarView;
    @BindView(R.id.fab_vaccine)
    FloatingActionButton mVaccineFAB;

    private Patient mPatient;
    private Child mChild;
    private SimpleDateFormat mDateFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination);

        ButterKnife.bind(this);
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        mPatient = getIntent().getParcelableExtra(Patient.PATIENT_CONSTANT);
        fetchVaccineRecords();
        mVaccineFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addVaccineRecord();
            }
        });

    }

    private void fetchVaccineRecords() {
        String selection = ChildColumns.AADHAAR + "=?";
        String selectionArgs[] = {mPatient.getmAadhaar()};
        Cursor cursor = getContentResolver().query(URI_CHILDREN, null, selection, selectionArgs, null);

        if (cursor != null) {
            ArrayList<Date> mHepBList, mTetanusList, mPneumoList, mRotaList;
            mHepBList = new ArrayList<>();
            mTetanusList = new ArrayList<>();
            mPneumoList = new ArrayList<>();
            mRotaList = new ArrayList<>();
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                try {
                    Date birthdate = mDateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(ChildColumns.BIRTHDATE)));
                    mHepBList.add(mDateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(ChildColumns.HEPB1))));
                    mHepBList.add(mDateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(ChildColumns.HEPB6))));
                    mTetanusList.add(mDateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(ChildColumns.TET2))));
                    mTetanusList.add(mDateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(ChildColumns.TET4))));
                    mTetanusList.add(mDateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(ChildColumns.TET6))));
                    mTetanusList.add(mDateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(ChildColumns.TET12))));
                    mTetanusList.add(mDateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(ChildColumns.TET18))));
                    mPneumoList.add(mDateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(ChildColumns.PNEUM2))));
                    mPneumoList.add(mDateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(ChildColumns.PNEUM4))));
                    mPneumoList.add(mDateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(ChildColumns.PNEUM12))));
                    mRotaList.add(mDateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(ChildColumns.ROTA2))));
                    mRotaList.add(mDateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(ChildColumns.ROTA4))));
                    mRotaList.add(mDateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(ChildColumns.ROTA6))));

                    mChild = new Child(mPatient.getmAadhaar(), birthdate, mHepBList, mTetanusList, mPneumoList, mRotaList);

                } catch (ParseException | NullPointerException e ) {
                    e.printStackTrace();
                }
            }
            setupCalendarView();

        }

    }

    private void setupCalendarView() {

        Date birthDate = mChild.getmBirthdate();
        ArrayList<Date> hepBDates, tetanusDates, pneumoDates, rotaDates;
        hepBDates = new ArrayList<>();
        tetanusDates = new ArrayList<>();
        pneumoDates = new ArrayList<>();
        rotaDates = new ArrayList<>();
        Timber.d("Birthdate is : "+ birthDate.toString());

        hepBDates.add(addMonths(birthDate, 1));
        hepBDates.add(addMonths(birthDate, 6));
        tetanusDates.add(addMonths(birthDate, 2));
        tetanusDates.add(addMonths(birthDate, 4));
        tetanusDates.add(addMonths(birthDate, 6));
        tetanusDates.add(addMonths(birthDate, 12));
        tetanusDates.add(addMonths(birthDate, 18));
        pneumoDates.add(addMonths(birthDate, 2));
        pneumoDates.add(addMonths(birthDate, 4));
        pneumoDates.add(addMonths(birthDate, 12));
        rotaDates.add(addMonths(birthDate, 2));
        rotaDates.add(addMonths(birthDate, 4));
        rotaDates.add(addMonths(birthDate, 6));

        mCalendarView.showCurrentMonthPage();


        List<EventDay> events = new ArrayList<>();

        for (Date date : hepBDates) {
            events.add(new EventDay(dateToCalendar(date), R.drawable.needle_black));
        }
        for (Date date : tetanusDates) {
            events.add(new EventDay(dateToCalendar(date), R.drawable.needle_black));
        }
        for (Date date : pneumoDates) {
            events.add(new EventDay(dateToCalendar(date), R.drawable.needle_black));
        }
        for (Date date : rotaDates) {
            events.add(new EventDay(dateToCalendar(date), R.drawable.needle_black));
        }

//        for (Date date : mChild.getmHepB()) {
//            events.add(new EventDay(dateToCalendar(date), R.drawable.needle));
//        }
//        for (Date date : mChild.getmTetanus()) {
//            events.add(new EventDay(dateToCalendar(date), R.drawable.needle));
//        }
//        for (Date date : mChild.getmPneumo()) {
//            events.add(new EventDay(dateToCalendar(date), R.drawable.needle));
//        }
//        for (Date date : mChild.getmRota()) {
//            events.add(new EventDay(dateToCalendar(date), R.drawable.needle));
//        }

        mCalendarView.setEvents(events);

    }

    private static Calendar dateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    private static Date addMonths(Date date, int i) {
        Timber.d("Adding months, original date is :" + date.toString());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);
        Timber.d(i+ "months added. Date is :" + cal.getTime().toString());
        return cal.getTime();
    }

    private void addVaccineRecord() {

    }
}

