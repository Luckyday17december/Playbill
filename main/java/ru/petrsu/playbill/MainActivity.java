// MainActivity.java
// Hosts the app's fragments and handles communication between them
package ru.petrsu.playbill;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import android.util.Log;
import android.os.AsyncTask;

public class MainActivity extends AppCompatActivity
        implements TeventsFragment.TeventsFragmentListener,
        DetailFragment.DetailFragmentListener/*,
        CalendarFragment.CalendarFragmentListener*/ {

    // key for storing a tevent's Uri in a Bundle passed to a fragment
    public static final String TEVENT_URI = "tevent_uri";

    private TeventsFragment teventsFragment; // displays tevent list

    // download db file from url using AsyncTask
    private void onDownloadComplete(boolean success) {
        // download complete log success
        Log.i("***", "************** " + success);
    }

    //
    private class Connection extends AsyncTask {

        @Override
        protected Object doInBackground(Object... arg0) {
            LoadFile();
            return null;
        }

    }

    private void LoadFile () {

            try {
                String destFileName = "Playbill.db";
                File dest = new File(getFilesDir().getParent() + "/databases/"+ destFileName);
                String src = "https://github.com/Luckyday17december/Playbill/raw/master/Playbill.db";
                FileUtils.copyURLToFile(new URL(src), dest);
                onDownloadComplete(true);
            } catch (IOException e) {
                e.printStackTrace();
                onDownloadComplete(false);
            }
    }

    // display TeventsFragment when MainActivity first loads
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // downloading db
        new Connection().execute();

        // if layout contains fragmentContainer, the layout is in use;
        // create and display a TeventsFragment
        if (savedInstanceState == null &&
                findViewById(R.id.fragmentContainer) != null) {
            // create TeventsFragment
            teventsFragment = new TeventsFragment();

            // add the fragment to the FrameLayout
            FragmentTransaction transaction =
                    getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragmentContainer, teventsFragment);
            transaction.commit(); // display TeventsFragment
        }
        else {
            teventsFragment =
                    (TeventsFragment) getSupportFragmentManager().
                            findFragmentById(R.id.teventsFragment);
        }
    }

    // display DetailFragment for selected tevent
    @Override
    public void onTeventSelected(Uri teventUri) {
        if (findViewById(R.id.fragmentContainer) != null) // phone
            displayTevent(teventUri, R.id.fragmentContainer);
        else { // tablet
            // removes top of back stack
            getSupportFragmentManager().popBackStack();

            displayTevent(teventUri, R.id.rightPaneContainer);
        }

    }

    /*// display CalendarFragment to view calendar
    @Override
    public void onAddTevent() {
        if (findViewById(R.id.fragmentContainer) != null) //
            displayCalendarFragment(R.id.fragmentContainer, null);
        else // tablet
            displayCalendarFragment(R.id.rightPaneContainer, null);
    }
*/
    // display a tevent
    private void displayTevent(Uri teventUri, int viewID) {
        DetailFragment detailFragment = new DetailFragment();

        // specify tevent's Uri as an argument to the DetailFragment
        Bundle arguments = new Bundle();
        arguments.putParcelable(TEVENT_URI, teventUri);
        detailFragment.setArguments(arguments);

        // use a FragmentTransaction to display the DetailFragment
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.replace(viewID, detailFragment);
        transaction.addToBackStack(null);
        transaction.commit(); // causes DetailFragment to display
    }
}
