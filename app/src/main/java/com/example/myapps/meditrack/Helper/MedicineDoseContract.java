package com.example.myapps.meditrack.Helper;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

 public class MedicineDoseContract {

     static final String CONTENT_AUTHORITY = "abhishek.meditrack";


     static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_MEDI_DOSE = "MedicineDosePath";

    public static final class MedicineDoseEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MEDI_DOSE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEDI_DOSE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEDI_DOSE;


        public static final String TABLE_NAME = "MedicineDose";
      //  public static final String COLUMN_MEDICINE_ID= "medicine_id";
        public static final String COLUMN_MEDICINE_NAME = "medicine_name";
        public static final String COLUMN_MEDICINE_QUANTITY = "medicine_number";
        public static final String COLUMN_NUMBER_OF_DOSE= "dose_number";
        public static final String COLUMN_DOSE_FREQUENCY = "dose_freq";
        public static final String COLUMN_DOSE_TIME = "dose_time";
        public static final String COLUMN_MEDICINE_PURCHASED_NUM = "num_medicine_buy";
        public static Uri buildMediTrackUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
}
