package com.example.myapps.meditrack;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapps.meditrack.Helper.MediDoseData;

import java.util.ArrayList;
import java.util.List;

public class MediTrackAdapter extends ArrayAdapter implements Filterable {
    private ArrayList<MediDoseData> filteredList;
    private Context context;
    private List<MediDoseData> items;
    private int resource;
    private final String TAG = "MediTrackAdapter";
    private MedicineFilter medicineFilter;

    public MediTrackAdapter(Context context, int resource, List<MediDoseData> data) {
        super(context, resource, data);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.items = data;
        this.resource = resource;
        this.filteredList = new ArrayList<>();
        this.filteredList.addAll(data);
        getFilter();
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
        viewHolder.MedicineName.setText(items.get(position).getMed_name());
        String dose=items.get(position).getDose_num()+" Dose";
        viewHolder.DoseNum.setText(dose);
        viewHolder.DoseTime.setText(items.get(position).getDose_time());
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
    public Filter getFilter() {
        if (medicineFilter == null) {
            medicineFilter = new MedicineFilter();
        }

        return medicineFilter;
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
