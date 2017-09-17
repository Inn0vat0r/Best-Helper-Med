package com.example.myapps.meditrack;

import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.example.myapps.meditrack.Helper.MediDoseData;
import com.example.myapps.meditrack.Helper.MedicineDoseContract;

import java.util.ArrayList;
import java.util.List;


public class TabFragment2 extends Fragment {
    ListView lvMedList;
    MediSearchAdapter adapter;
   private ArrayList<MediDoseData> medData;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        medData=new ArrayList<>();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_fragment1, container, false);
        lvMedList = (ListView) view.findViewById(R.id.lv_med);
        lvMedList.setEmptyView(view.findViewById(R.id.empty_data));
        adapter = new MediSearchAdapter(getActivity(), R.layout.med_list_item, medData);
        lvMedList.setAdapter(adapter);
        lvMedList.setTextFilterEnabled(false);
        getMediTrackData();
        return view;
    }
    private void getMediTrackData() {
        ContentResolver resolver = getActivity().getContentResolver();
        String[] projection = new String[]{MedicineDoseContract.MedicineDoseEntry.COLUMN_MEDICINE_NAME,
                MedicineDoseContract.MedicineDoseEntry.COLUMN_NUMBER_OF_DOSE,
                MedicineDoseContract.MedicineDoseEntry.COLUMN_DOSE_TIME,
                MedicineDoseContract.MedicineDoseEntry.COLUMN_MEDICINE_QUANTITY,
                MedicineDoseContract.MedicineDoseEntry.COLUMN_MEDICINE_PURCHASED_NUM,
                MedicineDoseContract.MedicineDoseEntry.COLUMN_DOSE_FREQUENCY,
                MedicineDoseContract.MedicineDoseEntry._ID


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
                    data.setMed_num(cursor.getInt(3));
                    data.setMed_num_pur(cursor.getInt(4));
                    data.setDose_freq(cursor.getString(5));
                    data.setMed_id(cursor.getInt(6));
                    medData.add(data);
                } while (cursor.moveToNext());
            }
            adapter.notifyDataSetChanged();
        }
        Log.i("Data",medData.toString());
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).setVisible(true).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if((query.isEmpty()))
                    adapter.getFilter().filter("");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if((query.isEmpty()))
                    adapter.getFilter().filter("");
                else adapter.getFilter().filter(query);
                return true;
            }

        });
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

}
