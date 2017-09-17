package com.example.myapps.meditrack;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.myapps.meditrack.Helper.AppPrefManager;

public class HomeScreen extends AppCompatActivity {

    private ViewPager _homeViewPager;
     TabLayout _homeTabLayout;
     HomeScreenAdapter adapter;
     AppPrefManager appInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        appInfo=new AppPrefManager(this);
        _homeViewPager=(ViewPager)findViewById(R.id.home_ViewPager);
        _homeTabLayout=(TabLayout)findViewById(R.id.home_tabLayout);
        _homeTabLayout.addTab(_homeTabLayout.newTab().setText("Tab1"));
        _homeTabLayout.addTab(_homeTabLayout.newTab().setText("Tab2"));
        _homeTabLayout.addTab(_homeTabLayout.newTab().setText("Tab3"));
        _homeTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
         adapter=new HomeScreenAdapter(getSupportFragmentManager(),_homeTabLayout.getTabCount());
        _homeViewPager.setAdapter(adapter);
        _homeTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                _homeViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
    }
    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        String callNum= appInfo.getSOSMobileNumber();
        if(!(callNum.startsWith("0")))
            callNum ="0"+callNum;

        switch (item.getItemId()) {
            case R.id.action_sos:
                if(!callNum.isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", callNum, null));
                    startActivity(intent);
                }
               /* BottomSheetDialogFragment bottomSheetDialogFragment = new MyDialogFragment();
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());*/
                break;
            case android.R.id.home:
                finish();
                break;
        }
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {

                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                Log.i(LOG_TAG, "OFFHOOK");
                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");
                    isPhoneCalling = false;
                }

            }
        }
    }
}
