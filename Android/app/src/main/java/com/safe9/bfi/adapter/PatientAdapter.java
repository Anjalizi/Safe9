package com.safe9.bfi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.safe9.bfi.R;
import com.safe9.bfi.model.Patient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter to populate patient data saved locally of users visiting the hospital; retrieved
 * from database on Android device
 */
public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {
    private List<Patient> mPatientList;
    private Context mContext;
    private UserOnClickHandler mClickHandler;

    public PatientAdapter(Context context, List<Patient> patients, UserOnClickHandler handler) {
        this.mContext = context;
        this.mPatientList = patients;
        this.mClickHandler = handler;
    }

    @Override
    public PatientAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View createdView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.patient_list_item, null);
        return new ViewHolder(createdView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Patient patient = mPatientList.get(position);
        viewHolder.mTVUserName.setText(patient.getName());
    }

    @Override
    public int getItemCount() {
        return mPatientList == null ? 0 : mPatientList.size();
    }

    public interface UserOnClickHandler{
        void onClick(Patient selectedPatient);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements
    View.OnClickListener{

        @BindView(R.id.tv_select_user_name)
        TextView mTVUserName;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            ButterKnife.bind(this,itemLayoutView);
            itemLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();

            Patient patient = mPatientList.get(adapterPosition);
            mClickHandler.onClick(patient);
        }
    }

    public List<Patient> getUserList() {
        return mPatientList;
    }
}