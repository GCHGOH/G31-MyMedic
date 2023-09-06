package com.example.madassignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class db_appointment {
    private static final String DB_APPOINTMENT = "appointment";
    public static final String DATABASE_TABLE = "appointment_table";
    public static final int DB_APPOINTMENT_VERSION = 1;

    private static final String doc_name = "Name";
    private static final String appointment_date = "Date";
    private static final String appointment_time = "Time";

    private static final String SCRIPT_CREATE_DATABASE = "create table " + DATABASE_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT," + doc_name + " text not null, " + appointment_date + " text not null, "+appointment_time+" text not null);";
    private static final String SCRIPT_UPDATE_DATABASE = "create table " + DATABASE_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT," + doc_name + " text not null, " + appointment_date + " text not null, "+appointment_time+" text not null);";

    private Context context;
    private SQLiteHelper db_appointment_helper;
    private SQLiteDatabase sqLiteDatabase;

    public db_appointment(Context c)
    {
        context = c;
    }

    public db_appointment opentoWrite() {
        db_appointment_helper = new SQLiteHelper(context, DATABASE_TABLE, null, DB_APPOINTMENT_VERSION);
        sqLiteDatabase = db_appointment_helper.getWritableDatabase();

        return this;
    }

    public db_appointment opentoRead() throws android.database.SQLException {
        db_appointment_helper = new db_appointment.SQLiteHelper(context, DATABASE_TABLE, null, DB_APPOINTMENT_VERSION);

        //Open to read
        sqLiteDatabase = db_appointment_helper.getReadableDatabase();

        return this;
    }


    public long insert_appointment(String content1, String content2,String content3) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(doc_name, content1);
        contentValues.put(appointment_date, content2);
        contentValues.put(appointment_time, content3);
        return sqLiteDatabase.insert(DATABASE_TABLE, null, contentValues);
    }

    public String queueAll()
    {
        String[] columns = new String[] {doc_name,appointment_date,appointment_time};
        Cursor cursor =sqLiteDatabase.query(DATABASE_TABLE, columns, null,null,null,null,null);
        String result="";
        int index_content = cursor.getColumnIndex(doc_name);
        int index_content2 = cursor.getColumnIndex(appointment_date);
        int index_content3 = cursor.getColumnIndex(appointment_time);

        for (cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext())
        {
            result = result + cursor.getString(index_content) + "; " +cursor.getString(index_content2) + "; "+cursor.getString(index_content3) + "; "+ "\n";
        }

        return result;
    }

    public void close()
    {
        db_appointment_helper.close();
    }

    public int deleteAll()
    {
        return sqLiteDatabase.delete(DATABASE_TABLE,null,null);
    }

    public class SQLiteHelper extends SQLiteOpenHelper{

        //Constructor with 4 parameter
        public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        //Create DB
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            sqLiteDatabase.execSQL(SCRIPT_CREATE_DATABASE);

        }

        //Ver. control
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            sqLiteDatabase.execSQL(SCRIPT_UPDATE_DATABASE);
        }
    }


}
