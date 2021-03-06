package com.example.manny.numberic;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.manny.numberic.model.model;

public class HighScoreActivity extends AppCompatActivity {

    private static final String TAG = "HighScoreActivity";

    private ContentValues cv;
    private model mModel;
    private SQLiteDatabase mDatabase;
    private SimpleCursorAdapter mAdapter;
    private static String COL_LEVELS = model.COL_LEVELS;
    private static String COL_TIME = model.COL_TIME;
    private static String TABLE_NAME = model.TABLE_NAME;
    private static String COL_SCORE = model.COL_SCORE;
    private String COL_ID = model.COL_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);


        mModel = new model(this);
        mDatabase = mModel.getWritableDatabase();

        Cursor cursor = readAllData();
        mAdapter = new SimpleCursorAdapter(this, R.layout.listview_layout , cursor,
                new String[]{COL_TIME},
                new int[] {R.id.showTime});
        ListView listView = (ListView) findViewById(R.id.highscore_listView);
        listView.setAdapter(mAdapter);
        // mAdapter = new SimpleCursorAdapter(this,and)
        // final ListView listView = (ListView) findViewById(R.id.highscore_listView);
        // listView.setAdapter(mAdapter);



        Button easy = (Button) findViewById(R.id.easy_button);
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] columns = {
                        model.COL_ID,COL_LEVELS, COL_TIME
                };

                Cursor cursor = mDatabase.rawQuery("SELECT * FROM "+ TABLE_NAME +" WHERE "+ COL_LEVELS + " =? " +" ORDER BY "+ COL_SCORE +" LIMIT 10 ",new String[]{"1"});

                mAdapter.changeCursor(cursor);
            }
        });
        Button medium = (Button) findViewById(R.id.medium_button);
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] columns = {
                        model.COL_ID, COL_LEVELS, COL_TIME
                };

                Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_LEVELS + " =? " + " ORDER BY " + COL_SCORE + " LIMIT 10 ", new String[]{"2"});
                mAdapter.changeCursor(cursor);
            }
        });
        Button hard = (Button) findViewById(R.id.hard_button);
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] columns = {
                        model.COL_ID, COL_LEVELS, COL_TIME
                };

                Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_LEVELS + " =? " + " ORDER BY " + COL_SCORE + " LIMIT 10 ", new String[]{"3"});
                mAdapter.changeCursor(cursor);
            }
        });

        Button btnReset = (Button) findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new android.support.v7.app.AlertDialog.Builder(HighScoreActivity.this)
                        .setTitle("Do you want to delete all record ?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDatabase.delete(TABLE_NAME,null,null);
                                Cursor cursor = readAllData();
                                mAdapter.changeCursor(cursor);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create()
                        .show();

            }
        });


    }


    private Cursor readAllData() {
        String[] columns = {
                model.COL_ID,COL_LEVELS,COL_SCORE,COL_TIME
        } ;
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM "+ TABLE_NAME +" WHERE "+ COL_LEVELS + " =? " +" ORDER BY "+ COL_SCORE +" LIMIT 10 ",new String[]{"1"});

        return cursor;
    }


}