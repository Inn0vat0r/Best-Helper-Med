package com.example.myapps.meditrack;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapps.meditrack.Helper.MediDoseData;
import com.example.myapps.meditrack.Helper.MedicineDoseContract;

import java.util.ArrayList;
import java.util.List;


public class TabFragment1 extends Fragment {
     ListView lvMedList;
     MediTrackAdapter adapter;
    private List<MediDoseData> medData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        medData=new ArrayList<>();
        setHasOptionsMenu(true);
    }

    private void getMediTrackData() {
        ContentResolver resolver = getActivity().getContentResolver();
        String[] projection = new String[]{MedicineDoseContract.MedicineDoseEntry.COLUMN_MEDICINE_NAME,
                MedicineDoseContract.MedicineDoseEntry.COLUMN_NUMBER_OF_DOSE,
                MedicineDoseContract.MedicineDoseEntry.COLUMN_DOSE_TIME
        };
        Cursor cursor =
                resolver.query(MedicineDoseContract.MedicineDoseEntry.CONTENT_URI,
                        projection,
                        null,
                        null,
                        null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    MediDoseData data = new MediDoseData();
                    data.setMed_name(cursor.getString(0));
                    data.setDose_num(String.valueOf(cursor.getInt(1)));
                    data.setDose_time(cursor.getString(2));
                    medData.add(data);
                } while (cursor.moveToNext());
            }
            adapter.notifyDataSetChanged();
        }
        Log.i("Data",medData.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment1, container, false);
        lvMedList = (ListView) view.findViewById(R.id.lv_med);
        lvMedList.setEmptyView(view.findViewById(R.id.empty_data));
        adapter = new MediTrackAdapter(getActivity(), R.layout.med_list_item, medData);
        lvMedList.setAdapter(adapter);
        getMediTrackData();
        return view;
    }
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
      if(medData.size()>0){
          {
              medData.clear();
              adapter.notifyDataSetChanged();
          }
          getMediTrackData();
      }

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_main, menu);
    }
}
