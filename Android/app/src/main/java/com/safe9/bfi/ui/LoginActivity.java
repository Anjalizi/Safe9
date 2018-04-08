package com.safe9.bfi.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.safe9.bfi.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Activity to login the radiologist in the app
 */
public class LoginActivity extends AppCompatActivity {

public static final String SHARED_PREFS_KEY = "prefs";
public static final String RADIO_NAME = "radiologist";

@BindView(R.id.btn_login)
Button mLoginButton;
@BindView(R.id.et_radio_name)
EditText mRadiologistName;
@BindView(R.id.pb_loading_indicator)
ProgressBar mProgressBar;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    ButterKnife.bind(this);

    final SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
    if (prefs.contains(RADIO_NAME)) {

        startMainActivity();
    }
    mLoginButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mProgressBar.setVisibility(View.VISIBLE);
            String radioName = mRadiologistName.getText().toString().trim();
            prefs.edit().putString(RADIO_NAME, radioName).apply();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mProgressBar.setVisibility(View.GONE);
                    startMainActivity();
                }
            }, 2000);

        }
    });

}

private void startMainActivity() {
    startActivity(new Intent(LoginActivity.this, MainActivity.class));
    finish();

}

}
