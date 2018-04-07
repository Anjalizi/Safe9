package com.safe9.bfi.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.safe9.bfi.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static android.os.Build.VERSION_CODES.M;

/**
 * Activity to display hospital and anganwadi mode
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.iv_hospital)
    ImageView mHospitalImageView;
    @BindView(R.id.iv_anganwadi)
    ImageView mAnganwadiImageView;

    private FusedLocationProviderClient mFusedLocationClient;
    private static final int PERMISSION_REQUEST_KEY = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Timber.plant(new Timber.DebugTree());
        ButterKnife.bind(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        setTitle(R.string.registered_patients);
        if (Build.VERSION.SDK_INT >= M) {
            checkPermission();
        }

        mHospitalImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SelectPatientActivity.class));
            }
        });
        mAnganwadiImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AnganwadiActivity.class));
            }
        });

    }

    public boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ) {
            requestPermission();
            return false;
        }
        return true;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                PERMISSION_REQUEST_KEY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_KEY: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission successfully granted", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(this, "Location is needed for the app to function", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

}
