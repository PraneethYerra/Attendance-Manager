package com.praneethcorporation.atendance;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText input;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu();
            }
        });
        listView = (ListView) findViewById(R.id.list);
        RelativeLayout empty_view = (RelativeLayout) findViewById(R.id.empty_view);
        listView.setEmptyView(empty_view);
        SubjectDbHElper subjectDbHElper = new SubjectDbHElper(this);
        SQLiteDatabase sqLiteDatabase = subjectDbHElper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SubjectContract.SubjectEntry.TABLE_NAME, null);

        SubjectAdapter subjectAdapter = new SubjectAdapter(this, cursor);

        listView.setAdapter(subjectAdapter);

        // Switch to new cursor and update contents of ListView
        subjectAdapter.changeCursor(cursor);

        subjectAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, attendance.class);
                TextView t = (TextView) view.findViewById(R.id.subjectname);
                String subjectName = t.getText().toString();
                intent.putExtra("key", subjectName);
                startActivity(intent);
            }
        });

    }


    public void menu() {
        AlertDialog.Builder alt = new AlertDialog.Builder(MainActivity.this);
        input = new EditText(this);
        alt.setView(input);
        alt.setIcon(R.drawable.subjects);
        alt.setMessage("Enter your Subject name here").setCancelable(false)
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SubjectDbHElper subjectDbHElper = new SubjectDbHElper(getApplicationContext());

                        SQLiteDatabase sqLiteDatabase = subjectDbHElper.getWritableDatabase();

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(SubjectContract.SubjectEntry.NAME_OF_THE_SUBJECT, input.getText().toString().trim());
                        Toast.makeText(getBaseContext(), "Subject has been added" + input.getText().toString().trim(), Toast.LENGTH_LONG).show();
                        sqLiteDatabase.insert(SubjectContract.SubjectEntry.TABLE_NAME, null, contentValues);
                        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SubjectContract.SubjectEntry.TABLE_NAME, null);

                        SubjectAdapter subjectAdapter = new SubjectAdapter(getApplicationContext(), cursor);

                        listView.setAdapter(subjectAdapter);

                        // Switch to new cursor and update contents of ListView
                        subjectAdapter.changeCursor(cursor);


                        subjectAdapter.notifyDataSetChanged();


                    }

                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alt.create();
        alertDialog.setTitle("Subject Name");
        alertDialog.show();

    }
}
