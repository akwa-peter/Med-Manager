package com.example.rad5.med_manager.Help_Classes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rad5.med_manager.R;

import java.util.ArrayList;

/**
 * Created by akwa on 4/6/18.
 */

public class MedicationAdapter extends ArrayAdapter<Medication> {

    private Medication currentMedication;

    public MedicationAdapter(@NonNull Context context, ArrayList<Medication> medications) {
        super(context, 0, medications);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.medication_item, null);
        }

        currentMedication = (Medication) getItem(position);

        TextView name = convertView.findViewById(R.id.txt_medication_name);
        name.setText(currentMedication.getDescription());

        TextView date = convertView.findViewById(R.id.txt_med_startDate);
        date.setText(currentMedication.getStartDate());

        return convertView;
    }
}
