// TeventsAdapter.java
// Subclass of RecyclerView.Adapter that binds tevents to RecyclerView
package ru.petrsu.playbill;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.petrsu.playbill.data.DatabaseDescription.Tevent;

public class TeventsAdapter
        extends RecyclerView.Adapter<TeventsAdapter.ViewHolder> {

    private Context context;

    // interface implemented by TeventsFragment to respond
    // when the user touches an item in the RecyclerView
    public interface TeventClickListener {
        void onClick(Uri teventUri);
    }

    // nested subclass of RecyclerView.ViewHolder used to implement
    // the view-holder pattern in the context of a RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView TitletextView;
        public final TextView DatetextView;
        private long rowID;

        // configures a RecyclerView item's ViewHolder
        public ViewHolder(View itemView) {
            super(itemView);
            TitletextView = (TextView) itemView.findViewById(android.R.id.text1);
            DatetextView = (TextView) itemView.findViewById(android.R.id.text2);

            // attach listener to itemView
            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        // executes when the tevent in this ViewHolder is clicked
                        @Override
                        public void onClick(View view) {
                            clickListener.onClick(Tevent.buildTeventUri(rowID));
                        }
                    }
            );
        }

        // set the database row ID for the tevent in this ViewHolder
        public void setRowID(long rowID) {
            this.rowID = rowID;
        }
    }

    // TeventsAdapter instance variables
    private Cursor cursor = null;
    private final TeventClickListener clickListener;

    // constructor
    public TeventsAdapter(TeventClickListener clickListener) {
        this.clickListener = clickListener;
    }

    // sets up new list item and its ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the android.R.layout.simple_list_item_1 layout
        View view = LayoutInflater.from(parent.getContext()).inflate(
                android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(view); // return current item's ViewHolder
    }

    // sets the text of the list item to display the search tag
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.setRowID(cursor.getLong(cursor.getColumnIndex(Tevent._ID)));
        holder.TitletextView.setText(cursor.getString(cursor.getColumnIndex(
                Tevent.COLUMN_TITLE)));
        holder.DatetextView.setText(cursor.getString(cursor.getColumnIndex(
                Tevent.COLUMN_DATE)));
    }

    // returns the number of items that adapter binds
    @Override
    public int getItemCount() {
        return (cursor != null) ? cursor.getCount() : 0;
    }

    // swap this adapter's current Cursor for a new one
    public void swapCursor(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }
}
