package com.example.myapps.meditrack;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapps.meditrack.Helper.MediDoseData;

import java.util.ArrayList;
import java.util.List;

public class MediSearchAdapter extends BaseAdapter implements Filterable {
    private ArrayList<MediDoseData> filteredList;
    private Context context;
    private ArrayList<MediDoseData> items;
    private int resource;
    private final String TAG = "MediTrackAdapter";
    private MedicineFilter medicineFilter;

    public MediSearchAdapter(Context context, int resource, ArrayList<MediDoseData> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.resource = resource;
        this.filteredList = data;
        //getFilter();
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int i) {
        return filteredList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        View rowView = convertView;
        if (rowView == null) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(resource, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.MedicineName = (TextView) rowView.findViewById(R.id.txt_med_name);
            viewHolder.DoseNum = (TextView) rowView.findViewById(R.id.txt_dose);
            viewHolder.DoseTime = (TextView) rowView.findViewById(R.id.txt_dose_time);
            viewHolder.medBox = (LinearLayout) rowView.findViewById(R.id.ll_med_box);
            rowView.setTag(viewHolder);
        }

        final ViewHolder viewHolder = (ViewHolder) rowView.getTag();
        viewHolder.MedicineName.setText(filteredList.get(position).getMed_name());
        String dose=filteredList.get(position).getDose_num()+" Dose";
        viewHolder.DoseNum.setText(dose);
        viewHolder.DoseTime.setText(filteredList.get(position).getDose_time());
        viewHolder.medBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent=new Intent(context,UpdateMedicineInfo.class);
                editIntent.putExtra("medName",filteredList.get(position).getMed_name());
                editIntent.putExtra("medNum",String.valueOf(filteredList.get(position).getMed_num()));
                editIntent.putExtra("doseNum",String.valueOf(filteredList.get(position).getDose_num()));
                editIntent.putExtra("doseTime",filteredList.get(position).getDose_time());
                editIntent.putExtra("medNumPur",String.valueOf(filteredList.get(position).getMed_num_pur()));
                if(filteredList.get(position).getDose_freq().equals("daily"))
                editIntent.putExtra("doseFreqNum",0);
                else editIntent.putExtra("doseFreqNum",1);
                editIntent.putExtra("medId",filteredList.get(position).getMed_id());
                context.startActivity(editIntent);

            }
        });
        return rowView;
    }
    private class ViewHolder {
        TextView MedicineName;
        TextView DoseNum;
        TextView DoseTime;
        LinearLayout medBox;
    }
    @NonNull
    @Override
   /* public Filter getFilter() {
        if (medicineFilter == null) {
            medicineFilter = new MedicineFilter();
        }

        return medicineFilter;
    }*/
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<MediDoseData> results = new ArrayList<MediDoseData>();
                if (items == null)
                    items = filteredList;
                if (constraint != null) {
                    if (items != null && items.size() > 0) {
                        for (final MediDoseData data : items) {
                            if (data.getMed_name().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(data);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                filteredList = (ArrayList<MediDoseData>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    private class MedicineFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<MediDoseData> tempList = new ArrayList<>();

                for (MediDoseData med : items) {
                    if (med.getMed_name().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(med);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = items.size();
                filterResults.values = items;
            }

            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList  = (ArrayList<MediDoseData>) results.values;
            notifyDataSetChanged();
        }
    }
  /*  public void FilterData(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            items.addAll(filteredList);
        }
        else
        {
            for (MediDoseData wp : filteredList)
            {
                if (wp.getMed_name().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    items.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }*/
}
