package com.safe9.bfi.ui;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.safe9.bfi.R;
import com.safe9.bfi.adapter.AnganPatientAdapter;
import com.safe9.bfi.model.AnganPatient;
import com.safe9.bfi.utils.QueryUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;


/**
 * Activity to display data obtained from server for Anganwadi worker
 * Location of current user is sent to server, server computes the nearby patients and sends back this data
 */
public class AnganwadiActivity extends AppCompatActivity implements QueryUtils.QueryCallback, AnganPatientAdapter.UserOnClickHandler {

    private FusedLocationProviderClient mFusedLocationClient;
    private static final int PERMISSION_REQUEST_KEY = 123;

    @BindView(R.id.pb_angan_loading_indicator)
    ProgressBar mLoadingIndicator;
    @BindView(R.id.tv_fetching_data)
    TextView mFetchingDataTextView;
    @BindView(R.id.rv_anganwadi)
    RecyclerView mRecyclerView;
    @BindView(R.id.rl_anganwadi_fetching)
    RelativeLayout mFetchingViews;

    private AnganPatientAdapter mAnganPatientAdapter;
    private ArrayList<AnganPatient> mPatientsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anganwadi);

        ButterKnife.bind(this);


        mRecyclerView.setVisibility(GONE);
        mFetchingViews.setVisibility(View.VISIBLE);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();

        setTitle(R.string.registered_patients);
    }

    private void getLocation() throws SecurityException {

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            handleLocation(location);
                        }
                    }
                });
    }


    private void handleLocation(Location location) {
        double mLatitude = location.getLatitude();
        double mLongitude = location.getLongitude();
        RequestQueue queue = Volley.newRequestQueue(this);
        QueryUtils.sendLocation(queue, mLatitude, mLongitude, this);

    }

    @Override
    public void returnLatLng(double lat, double lng) {

    }

    private void setupRecyclerView() {

        mFetchingViews.setVisibility(GONE);
        mAnganPatientAdapter = new AnganPatientAdapter(this, mPatientsList, this);

        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAnganPatientAdapter);
        mRecyclerView.setHasFixedSize(true);


    }

    @Override
    public void returnResponse(String response) {


        try {

            JSONObject json = new JSONObject(response);
            String nameJson = json.getString("name");
            String weekJson = json.getString("week");
            String latitudeJson = json.getString("latitude");
            String longitudeJson = json.getString("longitude");

            String[] names = nameJson.split(" ");
            String[] weeks = weekJson.split(" ");
            String[] lats = latitudeJson.split(" ");
            String[] longs = longitudeJson.split(" ");

            mPatientsList = new ArrayList<>();
            for (int i = 0; i < names.length; i++) {
                mPatientsList.add(new AnganPatient(names[i], Integer.valueOf(weeks[i]), Double.valueOf(lats[i])
                        , Double.valueOf(longs[i])));
            }
            setupRecyclerView();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(AnganPatient selectedAPatient) {
        Intent intent = new Intent(AnganwadiActivity.this, AnganPatientActivity.class);
        intent.putExtra(AnganPatient.ANGAN_PATIENT_CONSTANT, selectedAPatient);
        startActivity(intent);
    }
}
