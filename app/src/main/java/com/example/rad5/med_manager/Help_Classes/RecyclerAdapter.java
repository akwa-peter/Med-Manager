package com.example.rad5.med_manager.Help_Classes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rad5.med_manager.Add_Medication;
import com.example.rad5.med_manager.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by akwa on 4/6/18.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements Filterable{

    private Context context;
    private ArrayList<Medication> medications;
    private ArrayList<Medication> filteredMedications = new ArrayList<>();
    private CustomFilter mFilter;

    /**
     * Create a constructor for the class to get the context and the list of medications
     * @param context the context at which the list is found
     * @param medications the list of medications
     */
    public RecyclerAdapter(Context context, ArrayList<Medication> medications) {
        this.context = context;
        this.medications = medications;
        mFilter = new CustomFilter(RecyclerAdapter.this);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //infalte the view where the values would be binded to and return the view holder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medication_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //get the medication at the currently given position
        final Medication medication = medications.get(position);

        //now set the required value to the text views
        holder.medication_name.setText(medication.getName());
        holder.description.setText(medication.getStartDate());

        //set onClick listener on each of the list item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    Toast.makeText(context, "Click a list item at position " + getAdapterPosition(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, Add_Medication.class);
                intent.putExtra("intent", medication.getName());
                startActivity(context, intent, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        //return the total size of the medications list
        return medications.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView medication_name;
        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);

            //create the instance of the textview from the inflated view item
            medication_name = itemView.findViewById(R.id.txt_medication_name);
            description = itemView.findViewById(R.id.txt_med_startDate);

        }
    }


    public class CustomFilter extends Filter {
        private RecyclerAdapter mAdapter;
        private CustomFilter(RecyclerAdapter mAdapter) {
            super();
            this.mAdapter = mAdapter;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredMedications.clear();
            final FilterResults results = new FilterResults();
            if (constraint.length() == 0) {
                filteredMedications.addAll(medications);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for (Medication med : medications) {
                    if (med.getName().toLowerCase().startsWith(filterPattern)) {
                        filteredMedications.add(med);
                    }
                }
            }
//            System.out.println("Count Number " + filteredMedications.size());
            Log.d("debugger", "medications size = " + filteredMedications.size());
            results.values = filteredMedications;
            results.count = filteredMedications.size();
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            Log.d("debugger", "filtered results size = " + results.values);
//            System.out.println("Count Number 2 " + ((List<QuizObject>) results.values).size());
            this.mAdapter.notifyDataSetChanged();
        }
    }


}
