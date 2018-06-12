// DetailFragment.java
// Fragment subclass that displays one tevent's details
package ru.petrsu.playbill;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import ru.petrsu.playbill.data.DatabaseDescription.Tevent;

public class DetailFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    // callback methods implemented by MainActivity
    public interface DetailFragmentListener {
    }

    private static final int TEVENT_LOADER = 0; // identifies the Loader

    private DetailFragmentListener listener; // MainActivity
    private Uri teventUri; // Uri of selected tevent

    private TextView dateTextView; // displays tevent's date
    private TextView priceTextView; // displays tevent's price
    private TextView timeTextView; // displays tevent's time
    private TextView placeTextView; // displays tevent's place
    private TextView titleTextView; // displays tevent's title
    private TextView synopsisTextView; // displays tevent's synopsis
    private ImageView teventimageView;  // displays tevent's image

    // set DetailFragmentListener when fragment attached
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (DetailFragmentListener) context;
    }

    // remove DetailFragmentListener when fragment detached
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    // called when DetailFragmentListener's view needs to be created
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true); // this fragment has menu items to display

        // get Bundle of arguments then extract the tevent's Uri
        Bundle arguments = getArguments();

        if (arguments != null)
            teventUri = arguments.getParcelable(MainActivity.TEVENT_URI);

        // inflate DetailFragment's layout
        View view =
                inflater.inflate(R.layout.fragment_detail, container, false);

        // get the EditTexts
        dateTextView = (TextView) view.findViewById(R.id.dateTextView);
        priceTextView = (TextView) view.findViewById(R.id.priceTextView);
        timeTextView = (TextView) view.findViewById(R.id.timeTextView);
        placeTextView = (TextView) view.findViewById(R.id.placeTextView);
        titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        synopsisTextView = (TextView) view.findViewById(R.id.synopsisTextView);
        teventimageView = (ImageView) view.findViewById(R.id.teventImageView);

        // load the tevent
        getLoaderManager().initLoader(TEVENT_LOADER, null, this);
        return view;
    }

    // called by LoaderManager to create a Loader
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // create an appropriate CursorLoader based on the id argument;
        // only one Loader in this fragment, so the switch is unnecessary
        CursorLoader cursorLoader;

        switch (id) {
            case TEVENT_LOADER:
                cursorLoader = new CursorLoader(getActivity(),
                        teventUri, // Uri of tevent to display
                        null, // null projection returns all columns
                        null, // null selection returns all rows
                        null, // no selection arguments
                        null); // sort order
                break;
            default:
                cursorLoader = null;
                break;
        }

        return cursorLoader;
    }

    // called by LoaderManager when loading completes
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // if the tevent exists in the database, display its data
        if (data != null && data.moveToFirst()) {
            // get the column index for each data item
            int dateIndex = data.getColumnIndex(Tevent.COLUMN_DATE);
            int priceIndex = data.getColumnIndex(Tevent.COLUMN_PRICE);
            int timeIndex = data.getColumnIndex(Tevent.COLUMN_TIME);
            int placeIndex = data.getColumnIndex(Tevent.COLUMN_PLACE);
            int titleIndex = data.getColumnIndex(Tevent.COLUMN_TITLE);
            int synopsisIndex = data.getColumnIndex(Tevent.COLUMN_SYNOPSIS);
            int teventimageIndex = data.getColumnIndex(Tevent.COLUMN_IMAGE);

            // fill TextViews with the retrieved data
            dateTextView.setText(data.getString(dateIndex));
            priceTextView.setText(data.getString(priceIndex));
            timeTextView.setText(data.getString(timeIndex));
            placeTextView.setText(data.getString(placeIndex));
            titleTextView.setText(data.getString(titleIndex));
            synopsisTextView.setText(data.getString(synopsisIndex));
            // Using picasso library fill imageView from url
            Picasso.get().load(data.getString(teventimageIndex)).into(teventimageView);
        }
    }

    // called by LoaderManager when the Loader is being reset
    @Override
    public void onLoaderReset(Loader<Cursor> loader) { }
}
