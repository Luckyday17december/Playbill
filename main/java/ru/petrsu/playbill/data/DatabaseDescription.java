// DatabaseDescription.java
// Describes the table name and column names for this app's database,
// and other information required by the ContentProvider
package ru.petrsu.playbill.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseDescription {
    // ContentProvider's name: typically the package name
    public static final String AUTHORITY =
            "ru.petrsu.playbill.data";

    // base URI used to interact with the ContentProvider
    private static final Uri BASE_CONTENT_URI =
            Uri.parse("content://" + AUTHORITY);

    // nested class defines contents of the tevents table
    public static final class Tevent implements BaseColumns {
        public static final String TABLE_NAME = "tevents"; // table's name

        // Uri for the tevents table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        // column names for tevents table's columns
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_PLACE = "place";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_SYNOPSIS = "synopsis";
        public static final String COLUMN_IMAGE = "image url";

        // creates a Uri for a specific tevent
        public static Uri buildTeventUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
