package com.safe9.bfi.ui;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.safe9.bfi.R;
import com.safe9.bfi.data.ChildColumns;
import com.safe9.bfi.model.Child;
import com.safe9.bfi.model.Patient;
import com.safe9.bfi.model.Vaccine;
import com.safe9.bfi.utils.QueryUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static android.view.View.GONE;
import static com.safe9.bfi.data.PatientProvider.Children.URI_CHILDREN;

public class VaccinationActivity extends AppCompatActivity {

    @BindView(R.id.calendar_view)
    CompactCalendarView mCalendarView;
    @BindView(R.id.tv_vaccine_title)
    TextView mVaccineTitleTextView;
    @BindView(R.id.tv_vaccine_details)
    TextView mVaccineDetailsTextView;
    @BindView(R.id.btn_vaccine_submit)
    Button mVaccineSubmitButton;

    private Date mDateClicked;

    private static final String HEPATITIS = "Hepatitis B";
    private static final String TETANUS = "Tetanus";
    private static final String PNEUMO = "Pneumococcus";
    private static final String ROTA = "Rotavirus";

    ArrayList<Vaccine> hepBDates, tetanusDates, pneumoDates, rotaDates;
    private Patient mPatient;
    private Child mChild;
    private SimpleDateFormat mDateFormat, mMonthFormat, mDetailFormat;


    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination);
        ButterKnife.bind(this);

        mCalendarView.setFirstDayOfWeek(Calendar.MONDAY);

        mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        mDetailFormat = new SimpleDateFormat("dd-MMM");
        mMonthFormat = new SimpleDateFormat("MMM-yy");
        mPatient = getIntent().getParcelableExtra(Patient.PATIENT_CONSTANT);
        fetchVaccineRecords();
        mVaccineSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitVaccination();
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
                setupCalendarView();
            } else {
                mVaccineTitleTextView.setText("No Child Available");
            }
            cursor.close();
        }

    }

    private void setupCalendarView() {

        Date birthDate = mChild.getmBirthdate();
        hepBDates = new ArrayList<>();
        tetanusDates = new ArrayList<>();
        pneumoDates = new ArrayList<>();
        rotaDates = new ArrayList<>();
        Timber.d("Birthdate is : " + birthDate.toString());

        try {

            Date originalDate = mDateFormat.parse("1970-01-01");

            boolean b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13;
            b1 = !originalDate.equals(mChild.getmHepB().get(0));
            b2 = !originalDate.equals(mChild.getmHepB().get(1));
            b3 = !originalDate.equals(mChild.getmTetanus().get(0));
            b4 = !originalDate.equals(mChild.getmTetanus().get(1));
            b5 = !originalDate.equals(mChild.getmTetanus().get(2));
            b6 = !originalDate.equals(mChild.getmTetanus().get(3));
            b7 = !originalDate.equals(mChild.getmTetanus().get(4));
            b8 = !originalDate.equals(mChild.getmPneumo().get(0));
            b9 = !originalDate.equals(mChild.getmPneumo().get(1));
            b10 = !originalDate.equals(mChild.getmPneumo().get(2));
            b11 = !originalDate.equals(mChild.getmRota().get(0));
            b12 = !originalDate.equals(mChild.getmRota().get(1));
            b13 = !originalDate.equals(mChild.getmRota().get(2));
            hepBDates.add(new Vaccine(addMonths(birthDate, 1), b1, HEPATITIS, ChildColumns.HEPB1));
            hepBDates.add(new Vaccine(addMonths(birthDate, 6), b2, HEPATITIS, ChildColumns.HEPB6));
            tetanusDates.add(new Vaccine(addMonths(birthDate, 2), b3, TETANUS, ChildColumns.TET2));
            tetanusDates.add(new Vaccine(addMonths(birthDate, 4), b4, TETANUS, ChildColumns.TET4));
            tetanusDates.add(new Vaccine(addMonths(birthDate, 6), b5, TETANUS, ChildColumns.TET6));
            tetanusDates.add(new Vaccine(addMonths(birthDate, 12), b6, TETANUS, ChildColumns.TET12));
            tetanusDates.add(new Vaccine(addMonths(birthDate, 18), b7, TETANUS, ChildColumns.TET18));
            pneumoDates.add(new Vaccine(addMonths(birthDate, 2), b8, PNEUMO, ChildColumns.PNEUM2));
            pneumoDates.add(new Vaccine(addMonths(birthDate, 4), b9, PNEUMO, ChildColumns.PNEUM4));
            pneumoDates.add(new Vaccine(addMonths(birthDate, 12), b10, PNEUMO, ChildColumns.PNEUM12));
            rotaDates.add(new Vaccine(addMonths(birthDate, 2), b11, ROTA, ChildColumns.ROTA2));
            rotaDates.add(new Vaccine(addMonths(birthDate, 4), b12, ROTA, ChildColumns.ROTA4));
            rotaDates.add(new Vaccine(addMonths(birthDate, 6), b13, ROTA, ChildColumns.ROTA6));

        } catch (ParseException e) {
            e.printStackTrace();
        }


        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        maxDate.add(Calendar.YEAR, 2);


        for (Vaccine vaccine : hepBDates) {

            mCalendarView.addEvent(new Event(R.color.colorAccent, vaccine.getmDate().getTime(), vaccine));

        }
        for (Vaccine vaccine : tetanusDates) {
            mCalendarView.addEvent(new Event(R.color.colorAccent, vaccine.getmDate().getTime(), vaccine));

        }
        for (Vaccine vaccine : pneumoDates) {
            mCalendarView.addEvent(new Event(R.color.colorAccent, vaccine.getmDate().getTime(), vaccine));

        }
        for (Vaccine vaccine : rotaDates) {

            mCalendarView.addEvent(new Event(R.color.colorAccent, vaccine.getmDate().getTime(), vaccine));
        }

        setTitle(mMonthFormat.format(mCalendarView.getFirstDayOfCurrentMonth()));

        List<Event> events = mCalendarView.getEvents(Calendar.getInstance().getTime());
        if (events.size() == 0) {
            mVaccineDetailsTextView.setText("");
            mVaccineTitleTextView.setText("No Vaccines");
            mVaccineSubmitButton.setVisibility(GONE);
        } else {
            Vaccine vaccine = (Vaccine) events.get(0).getData();
            String title = vaccine.getmTitle();
            for (int i = 1; i < events.size(); i++) {
                title = title.concat("\n" + ((Vaccine) events.get(i).getData()).getmTitle());
            }
            mVaccineTitleTextView.setText(title);
            String details;
            details = "Vaccination due on " + mDetailFormat.format(Calendar.getInstance().getTime());
            if (!vaccine.isDone()) {
                mVaccineSubmitButton.setVisibility(View.VISIBLE);
            } else {
                mVaccineSubmitButton.setVisibility(GONE);
                details = details.concat("\nCompleted");
            }
            mVaccineDetailsTextView.setText(details);
        }


        mCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                if (mChild != null) {

                    mDateClicked = dateClicked;
                    List<Event> events = mCalendarView.getEvents(dateClicked);
                    Log.d("sd", "Day was clicked: " + dateClicked + " with events " + events);
                    if (events.size() == 0) {
                        mVaccineDetailsTextView.setText("");
                        mVaccineTitleTextView.setText("No Vaccines");
                        mVaccineSubmitButton.setVisibility(GONE);
                    } else {
                        Vaccine vaccine = (Vaccine) events.get(0).getData();
                        String title = vaccine.getmTitle();
                        for (int i = 1; i < events.size(); i++) {
                            title = title.concat("\n" + ((Vaccine) events.get(i).getData()).getmTitle());
                        }
                        mVaccineTitleTextView.setText(title);
                        String details;
                        details = "Vaccination due on " + mDetailFormat.format(dateClicked);
                        if (!vaccine.isDone()) {
                            mVaccineSubmitButton.setVisibility(View.VISIBLE);
                        } else {
                            mVaccineSubmitButton.setVisibility(GONE);
                            details = details.concat("\nCompleted");
                        }
                        mVaccineDetailsTextView.setText(details);
                    }
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Log.d("Sd", "Month was scrolled to: " + firstDayOfNewMonth);
                setTitle(mMonthFormat.format(firstDayOfNewMonth));
            }
        });


        for (Date date : mChild.getmHepB()) {
            mCalendarView.addEvent(new Event(R.color.colorAccent, date.getTime(), new Vaccine(date, true, HEPATITIS)));
        }
        for (Date date : mChild.getmTetanus()) {
            mCalendarView.addEvent(new Event(R.color.colorAccent, date.getTime(), new Vaccine(date, true, TETANUS)));
        }
        for (Date date : mChild.getmPneumo()) {
            mCalendarView.addEvent(new Event(R.color.colorAccent, date.getTime(), new Vaccine(date, true, PNEUMO)));
        }
        for (Date date : mChild.getmRota()) {
            mCalendarView.addEvent(new Event(R.color.colorAccent, date.getTime(), new Vaccine(date, true, ROTA)));
        }


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
        Timber.d(i + "months added. Date is :" + cal.getTime().toString());
        return cal.getTime();
    }

    private void submitVaccination() {
        List<Event> events = mCalendarView.getEvents(mDateClicked);
        for (Event event : events) {
            Vaccine vaccine = (Vaccine) event.getData();
            if (vaccine != null) {
                try {
                    Timber.d("Current calendar date is :" + Calendar.getInstance().getTime().toString());
                    String date = mDateFormat.format(Calendar.getInstance().getTime());
                    Timber.d("String date is " + date);
                    Date currentDate = mDateFormat.parse(date);
                    Timber.d("Current date is " + currentDate.toString());
                    vaccine.setmCompleteDate(currentDate);
                    String selection = ChildColumns.AADHAAR + "=?";
                    String selectionArgs[] = {mChild.getmAadhaar()};
                    ContentValues values = new ContentValues();
                    values.put(vaccine.getDBKey(), date);
                    getContentResolver().update(URI_CHILDREN, values, selection, selectionArgs);

                    String urlParams = "?injection=" + vaccine.getDBKey() + "&aadhar=" + mChild.getmAadhaar();
                    RequestQueue queue = Volley.newRequestQueue(this);
                    QueryUtils.sendVaccineDataRequest(queue, urlParams);
                    Toast.makeText(this, "Vaccination succesfully done", Toast.LENGTH_SHORT).show();
                    finish();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}

