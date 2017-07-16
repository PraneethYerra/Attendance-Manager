package com.praneethcorporation.atendance;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class attendance extends AppCompatActivity {

    private TextView Subject_name,noofclassesattended,nooftotalclasses;
    private TextView Percentage;
    Button attend,bunk,addclass,removeclass;
    ListView listview;
    float ratio;
    String mSubjectName;
int attendedclasses,totalclasses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);


        Subject_name = (TextView) findViewById(R.id.subjectname);
         noofclassesattended = (TextView) findViewById(R.id.no_of_classes_attended);
         nooftotalclasses = (TextView) findViewById(R.id.no_of_total_classes);
        Percentage = (TextView) findViewById(R.id.percentage);

        Intent intent = getIntent();

        final String name = intent.getStringExtra("key").trim();

mSubjectName = name;

        SubjectDbHElper subjectDbHElper = new SubjectDbHElper(this);
        final SQLiteDatabase sqLiteDatabase = subjectDbHElper.getReadableDatabase();

        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM "+ SubjectContract.SubjectEntry.TABLE_NAME +" WHERE "+ SubjectContract.SubjectEntry.NAME_OF_THE_SUBJECT+" = '"+name+"'",null);

        if(data.moveToFirst()) {
            int nameofSubject = data.getColumnIndex(SubjectContract.SubjectEntry.NAME_OF_THE_SUBJECT);

            int noOfPresents = data.getColumnIndex(SubjectContract.SubjectEntry.NO_OF_PRESENTS);

            int noOfTotalClasses = data.getColumnIndex(SubjectContract.SubjectEntry.NO_OF_TOTAL_CLASSES);

            int percentage = data.getColumnIndex(SubjectContract.SubjectEntry.PERCENTAGE);


            String SUBJECTNAME = data.getString(nameofSubject);

            int PRESENTS = data.getInt(noOfPresents);


            int TOTAL = data.getInt(noOfTotalClasses);

            int PERCENTAGE = data.getInt(percentage);

            String presents = Integer.toString(PRESENTS);

            String total = Integer.toString(TOTAL);

            String percentageofattendance = Integer.toString(PERCENTAGE);


            Subject_name.setText(SUBJECTNAME);

            noofclassesattended.setText(presents);

            nooftotalclasses.setText(total);

            Percentage.setText(percentageofattendance);


        }

        attend = (Button) findViewById(R.id.attended);
        attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendedclasses = Integer.parseInt(noofclassesattended.getText().toString());
                attendedclasses = attendedclasses + 1;
                totalclasses = Integer.parseInt(nooftotalclasses.getText().toString());
                totalclasses = totalclasses + 1;
                updateattendance();
            }
        });

        bunk = (Button) findViewById(R.id.bunkedButton);
        bunk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendedclasses = Integer.parseInt(noofclassesattended.getText().toString());
                totalclasses = Integer.parseInt(nooftotalclasses.getText().toString());
                totalclasses = totalclasses + 1;
                updateattendance();
            }
        });

        addclass = (Button) findViewById(R.id.addclass);
        addclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendedclasses = Integer.parseInt(noofclassesattended.getText().toString());
                totalclasses = Integer.parseInt(nooftotalclasses.getText().toString());
                if (totalclasses > attendedclasses) {
                    attendedclasses = attendedclasses + 1;
                    updateattendance();
                } else
                    return;
            }
        });

        removeclass = (Button) findViewById(R.id.removeclass);
        removeclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                attendedclasses = Integer.parseInt(noofclassesattended.getText().toString());
                totalclasses = Integer.parseInt(nooftotalclasses.getText().toString());
                if (attendedclasses != 0) {
                    attendedclasses = attendedclasses - 1;
                    updateattendance();
                } else
                    return;
            }
        });


        Button storevalues = (Button) findViewById(R.id.storevalues);
        storevalues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
sqLiteDatabase.execSQL("UPDATE "+ SubjectContract.SubjectEntry.TABLE_NAME+" SET "+ SubjectContract.SubjectEntry.NO_OF_PRESENTS +" = "+attendedclasses+","+ SubjectContract.SubjectEntry.NO_OF_TOTAL_CLASSES+" = "+totalclasses+","+ SubjectContract.SubjectEntry.PERCENTAGE +" = "+ratio+" WHERE "+ SubjectContract.SubjectEntry.NAME_OF_THE_SUBJECT+" = '"+name+"'");
            finish();
            }

        });




    }

    public void updateattendance() {
        nooftotalclasses.setText(String.valueOf(totalclasses));
        noofclassesattended.setText(String.valueOf(attendedclasses));
        ratio = (attendedclasses / (float) totalclasses) * 100;
        Percentage.setText(String.valueOf(ratio));

    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.attendancemenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       showdeleteConfiramtiondialog();

        return true;
    }

    private void showdeleteConfiramtiondialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("DELETE THIS SUBJECT?");
        builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteSubject();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog!=null)
                    dialog.dismiss();
            }
        });
    AlertDialog alerDialog = builder.create();
        alerDialog.show();
    }

    private void deleteSubject() {
        SubjectDbHElper subjectDbHElper = new SubjectDbHElper(this);
        SQLiteDatabase db = subjectDbHElper.getWritableDatabase();
        db.execSQL("DELETE FROM "+ SubjectContract.SubjectEntry.TABLE_NAME+" WHERE "+ SubjectContract.SubjectEntry.NAME_OF_THE_SUBJECT+" = '"+mSubjectName+"'");

        Cursor cursor = db.rawQuery("SELECT * FROM "+ SubjectContract.SubjectEntry.TABLE_NAME,null);
        SubjectAdapter subjectAdapter = new SubjectAdapter(this,cursor);
        subjectAdapter.swapCursor(cursor);
        subjectAdapter.notifyDataSetChanged();
        finish();
    }
}
