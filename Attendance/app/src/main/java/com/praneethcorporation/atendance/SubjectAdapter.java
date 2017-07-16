package com.praneethcorporation.atendance;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by user on 7/16/2017.
 */

class SubjectAdapter extends CursorAdapter {
    public SubjectAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.single_subject,null,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView subject_name = (TextView)view.findViewById(R.id.subjectname);
//        Button percentage = (Button)view.findViewById(R.id.percentage);

        int subjectNameColumnIndex = cursor.getColumnIndex(SubjectContract.SubjectEntry.NAME_OF_THE_SUBJECT);
       // int PercentageCl oumnIndex = cursor.getColumnIndex(SubjectContract.SubjectEntry.PERCENTAGE);

        String SubjectName = cursor.getString(subjectNameColumnIndex);
        //    int Percentage = cursor.getInt(PercentageCloumnIndex);

        subject_name.setText(SubjectName);
        //      percentage.setText(Integer.toString(Percentage));
    }
}
