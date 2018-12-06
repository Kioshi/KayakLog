package cz.martinek.stepan.kayaklog;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import cz.martinek.stepan.kayaklog.database.DBHelper;

public class tripCursorAdapter extends CursorAdapter {

    public tripCursorAdapter(Context context, Cursor cursor){
        super(context, cursor, 0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_trip, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        DBHelper dbHandler = new DBHelper(context, null,null,1);
        Cursor tripCursor = dbHandler.getListCursor();

        String name = tripCursor.getString(3);
        TextView tripName = (TextView) view.findViewById(R.id.tripName);
        //TextView tripDescription = (TextView) view.findViewById(R.id.tripDescription);


        //String name = tripCursor.getString(cursor.getColumnIndexOrThrow("name"));
        //String description = cursor.getString(cursor.getColumnIndexOrThrow("desc"));

        //Setting values from cursor to the text view
        tripName.setText(name);
        //tripDescription.setText(description);



    }
}
