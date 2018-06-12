// PlaybillContentProvider.java
// ContentProvider subclass for manipulating the app's database
package ru.petrsu.playbill.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import ru.petrsu.playbill.R;
import ru.petrsu.playbill.data.DatabaseDescription.Tevent;

public class PlaybillContentProvider extends ContentProvider {
    // used to access the database
    private PlaybillDatabaseHelper dbHelper;

    // UriMatcher helps ContentProvider determine operation to perform
    private static final UriMatcher uriMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);

    // constants used with UriMatcher to determine operation to perform
    private static final int ONE_TEVENT = 1; // manipulate one tevent
    private static final int TEVENTS = 2; // manipulate tevents table

    // static block to configure this ContentProvider's UriMatcher
    static {
        // Uri for Tevent with the specified id (#)
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                Tevent.TABLE_NAME + "/#", ONE_TEVENT);

        // Uri for Tevents table
        uriMatcher.addURI(DatabaseDescription.AUTHORITY,
                Tevent.TABLE_NAME, TEVENTS);
    }

    // called when the PlaybillContentProvider is created
    @Override
    public boolean onCreate() {
        // create the PlaybillDatabaseHelper
        dbHelper = new PlaybillDatabaseHelper(getContext());
        return true; // ContentProvider successfully created
    }

    // required method: Not used in this app, so we return null
    @Override
    public String getType(Uri uri) {
        return null;
    }

    // query the database
    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs, String sortOrder) {

        // create SQLiteQueryBuilder for querying tevents table
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(Tevent.TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case ONE_TEVENT: // tevent with specified id will be selected
                queryBuilder.appendWhere(
                        Tevent._ID + "=" + uri.getLastPathSegment());
                break;
            case TEVENTS: // all tevents will be selected
                break;
            default:
                throw new UnsupportedOperationException(
                        getContext().getString(R.string.invalid_query_uri) + uri);
        }

        // execute the query to select one or all tevents
        Cursor cursor = queryBuilder.query(dbHelper.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);

        // configure to watch for content changes
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    // required method: Not used in this app, so we return null
    @Override
    public Uri insert(Uri uri, ContentValues values) {
      return null;
    }

    // required method: Not used in this app, so we return 0
    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {
       return 0;
    }

    // required method: Not used in this app, so we return 0
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }
}
