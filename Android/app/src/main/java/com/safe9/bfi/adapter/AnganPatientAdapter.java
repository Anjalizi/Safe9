package com.safe9.bfi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.safe9.bfi.R;
import com.safe9.bfi.model.AnganPatient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter to populate patient data for Anganwadi workers obtained from server
 */
public class AnganPatientAdapter extends RecyclerView.Adapter<AnganPatientAdapter.ViewHolder> {
    private List<AnganPatient> mAnganPatientList;
    private Context mContext;
    private AnganPatientAdapter.UserOnClickHandler mClickHandler;

    public AnganPatientAdapter(Context context, List<AnganPatient> patients, AnganPatientAdapter.UserOnClickHandler handler) {
        this.mContext = context;
        this.mAnganPatientList = patients;
        this.mClickHandler = handler;
    }

    @Override
    public AnganPatientAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View createdView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.anganwadi_list_item,null);
        return new AnganPatientAdapter.ViewHolder(createdView);
    }

    @Override
    public void onBindViewHolder(AnganPatientAdapter.ViewHolder viewHolder, int position) {
        AnganPatient patient = mAnganPatientList.get(position);
        viewHolder.mNameTextView.setText(patient.getmName());
        viewHolder.mWeeksTextView.setText(String.valueOf(patient.getmWeeks())+ " weeks");
    }

    @Override
    public int getItemCount() {
        return mAnganPatientList == null ? 0 : mAnganPatientList.size();
    }

    public interface UserOnClickHandler {
        void onClick(AnganPatient selectedAPatient);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        @BindView(R.id.tv_anganwadi_patient_name)
        TextView mNameTextView;
        @BindView(R.id.tv_anganwadi_patient_weeks)
        TextView mWeeksTextView;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            ButterKnife.bind(this, itemLayoutView);
            itemLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();

            AnganPatient patient = mAnganPatientList.get(adapterPosition);
            mClickHandler.onClick(patient);
        }
    }

    public List<AnganPatient> getPatientList() {
        return mAnganPatientList;
    }
}