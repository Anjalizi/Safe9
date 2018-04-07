package com.safe9.bfi.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
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
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.safe9.bfi.data.PatientProvider.Children.URI_CHILDREN;

public class VaccinationActivity extends AppCompatActivity implements CalendarPickerController {

//    @BindView(R.id.calendar_view_vaccination)
//    CalendarView mCalendarView;
    @BindView(R.id.fab_vaccine)
    FloatingActionButton mVaccineFAB;
    @BindView(R.id.agenda_calendar_view)
    AgendaCalendarView mAgendaCalendarView;

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

                } catch (ParseException | NullPointerException e) {
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
        Timber.d("Birthdate is : " + birthDate.toString());

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


        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        maxDate.add(Calendar.YEAR, 2);

        List<CalendarEvent> eventList = new ArrayList<>();


//        mCalendarView.showCurrentMonthPage();


//        List<EventDay> events = new ArrayList<>();


        Calendar startTime = dateToCalendar(birthDate);

        for (Date date : hepBDates) {
            BaseCalendarEvent event1 = new BaseCalendarEvent("Hepatitis Vaccine", "HepB 125", "",
                    ContextCompat.getColor(this, R.color.colorAccent), dateToCalendar(date),dateToCalendar(date), true);
            eventList.add(event1);
        }
        for (Date date : tetanusDates) {
            BaseCalendarEvent event1 = new BaseCalendarEvent("Tetanus Vaccine", "TET 125", "",
                    ContextCompat.getColor(this, R.color.colorAccent), dateToCalendar(date),dateToCalendar(date), true);
            eventList.add(event1);
        }
        for (Date date : pneumoDates) {
            BaseCalendarEvent event1 = new BaseCalendarEvent("Pneumo Vaccine", "PNEUM 125", "",
                    ContextCompat.getColor(this, R.color.colorAccent), dateToCalendar(date),dateToCalendar(date), true);
            eventList.add(event1);
        }
        for (Date date : rotaDates) {
            BaseCalendarEvent event1 = new BaseCalendarEvent("Rota Vaccine", "ROT 125", "",
                    ContextCompat.getColor(this, R.color.colorAccent), dateToCalendar(date),dateToCalendar(date), true);
            eventList.add(event1);
        }
        mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), this);

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

      /*  mCalendarView.setEvents(events);

        mCalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                int resId = eventDay.getImageResource();
                switch (resId) {
                    case R.drawable.needle_blue:
                        Toast.makeText(VaccinationActivity.this, "Blue", Toast.LENGTH_SHORT).show();
                        break;
                    case R.drawable.needle_green:
                        Toast.makeText(VaccinationActivity.this, "Green", Toast.LENGTH_SHORT).show();
                        break;
                    case R.drawable.needle_yellow:
                        Toast.makeText(VaccinationActivity.this, "Yellow", Toast.LENGTH_SHORT).show();
                        break;
                    case R.drawable.needle_red:
                        Toast.makeText(VaccinationActivity.this, "Red", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }

            }
        });*/

    }


/*

    private void mockList(List<CalendarEvent> eventList) {
        Calendar startTime1 = Calendar.getInstance();
        Calendar endTime1 = Calendar.getInstance();
        endTime1.add(Calendar.MONTH, 1);
        BaseCalendarEvent event1 = new BaseCalendarEvent("Thibault travels in Iceland", "A wonderful journey!", "Iceland",
                ContextCompat.getColor(this, R.color.colorAccent), startTime1, endTime1, true);
        eventList.add(event1);

        Calendar startTime2 = Calendar.getInstance();
        startTime2.add(Calendar.DAY_OF_YEAR, 1);
        Calendar endTime2 = Calendar.getInstance();
        endTime2.add(Calendar.DAY_OF_YEAR, 3);
        BaseCalendarEvent event2 = new BaseCalendarEvent("Visit to Dalvík", "A beautiful small town", "Dalvík",
                ContextCompat.getColor(this, R.color.yellow), startTime2, endTime2, true);
        eventList.add(event2);

        // Example on how to provide your own layout
        Calendar startTime3 = Calendar.getInstance();
        Calendar endTime3 = Calendar.getInstance();
        startTime3.set(Calendar.HOUR_OF_DAY, 14);
        startTime3.set(Calendar.MINUTE, 0);
        endTime3.set(Calendar.HOUR_OF_DAY, 15);
        endTime3.set(Calendar.MINUTE, 0);
        DrawableCalendarEvent event3 = new DrawableCalendarEvent("Visit of Harpa", "", "Dalvík",
                ContextCompat.getColor(this, R.color.blue_dark), startTime3, endTime3, false, R.drawable.common_ic_googleplayservices);
        eventList.add(event3);
    }

*/
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
        Timber.d(i + "months added. Date is :" + cal.getTime().toString());
        return cal.getTime();
    }

    private void addVaccineRecord() {

    }

    @Override
    public void onDaySelected(DayItem dayItem) {

    }

    @Override
    public void onEventSelected(CalendarEvent event) {

    }

    @Override
    public void onScrollToDate(Calendar calendar) {

    }
}

