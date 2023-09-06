package com.example.madassignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class db_consulter {
    private static final String DB_CONSULTER = "consulter";
    public static final String DATABASE_TABLE = "consulter_table";
    public static final int DB_CONSULTER_VERSION = 1;

    private static final String con_name = "Name";
    private static final String con_addr = "Address";

    private static final String SCRIPT_CREATE_DATABASE = "create table " + DATABASE_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT," + con_name + " text not null, " + con_addr + " text);";
    private static final String SCRIPT_UPDATE_DATABASE = "create table " + DATABASE_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT," + con_name + " text not null, " + con_addr + " text);";

    private Context context;
    private SQLiteHelper db_consulter_helper;
    private SQLiteDatabase sqLiteDatabase;

    public db_consulter(Context c) {
        context = c;
    }


    public db_consulter opentoWrite() {
        db_consulter_helper = new SQLiteHelper(context, DATABASE_TABLE, null, DB_CONSULTER_VERSION);
        sqLiteDatabase = db_consulter_helper.getWritableDatabase();

        return this;
    }

    public db_consulter opentoRead() {
        db_consulter_helper = new db_consulter.SQLiteHelper(context, DATABASE_TABLE, null, DB_CONSULTER_VERSION);

        //Open to read
        sqLiteDatabase = db_consulter_helper.getReadableDatabase();

        return this;
    }

    public long insert_consulter(String content1, String content2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(con_name, content1);
        contentValues.put(con_addr, content2);
        return sqLiteDatabase.insert(DATABASE_TABLE, null, contentValues);
    }

    public String queueAll()
    {
        String[] columns = new String[] {con_name,con_addr};
        Cursor cursor =sqLiteDatabase.query(DATABASE_TABLE, columns, null,null,null,null,null);
        String result="";
        int index_content = cursor.getColumnIndex(con_name);
        int index_content2 = cursor.getColumnIndex(con_addr);

        for (cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext())
        {
            result = result + cursor.getString(index_content) + "; " +cursor.getString(index_content2) + "; "+ "\n";
        }

        return result;
    }

    public void close()
    {
        db_consulter_helper.close();
    }

    public int deleteAll()
    {
        return sqLiteDatabase.delete(DATABASE_TABLE,null,null);
    }

    //Super class of SQLiteHelper ->Implement both override method to create DB
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



