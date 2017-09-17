package com.example.myapps.meditrack.Helper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MedicineTrackProvider extends ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DBHandler mOpenHelper;

    static final int MEDICINE_ENTRY=401;
    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MedicineDoseContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, MedicineDoseContract.PATH_MEDI_DOSE,MEDICINE_ENTRY);
        return matcher;
    }
    @Override
    public boolean onCreate() {
        mOpenHelper = new DBHandler(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case MEDICINE_ENTRY: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MedicineDoseContract.MedicineDoseEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
           /* case BOOKSERVICE: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        BookServiceContract.BookServiceEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }*/

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
        }

    @Override
    public String getType(Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
              case MEDICINE_ENTRY:
                return MedicineDoseContract.MedicineDoseEntry.CONTENT_TYPE;
            /*case BOOKSERVICE:
                return BookServiceContract.BookServiceEntry.CONTENT_TYPE;*/

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case MEDICINE_ENTRY: {
                long _id = db.insert(MedicineDoseContract.MedicineDoseEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MedicineDoseContract.MedicineDoseEntry.buildMediTrackUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

           /* case BOOKSERVICE: {
                long _id = db.insert(BookServiceContract.BookServiceEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = BookServiceContract.BookServiceEntry.buildBookServiceUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }*/
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
        }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case MEDICINE_ENTRY:
                rowsDeleted = db.delete(
                        MedicineDoseContract.MedicineDoseEntry.TABLE_NAME, selection, selectionArgs);
                break;
          /*  case BOOKSERVICE:
                rowsDeleted = db.delete(
                        BookServiceContract.BookServiceEntry.TABLE_NAME, selection, selectionArgs);
                break;*/
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case MEDICINE_ENTRY:
                rowsUpdated = db.update(MedicineDoseContract.MedicineDoseEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
           /* case BOOKSERVICE:
                rowsUpdated = db.update(BookServiceContract.BookServiceEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;*/
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
        }
    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MEDICINE_ENTRY:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        normalizeDate(value);
                        long _id = db.insert(MedicineDoseContract.MedicineDoseEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    private void normalizeDate(ContentValues value) {

    }


}
