package com.example.madassignment2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "my_medic.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "users";
    public static final String ROW_ID = "_id";
    public static final String ROW_NAME = "name";
    public static final String ROW_PHONE = "phone";
    public static final String ROW_EMAIL = "email";
    public static final String ROW_PASSWORD = "password";

    //Memo Page table info
    public static final String TABLE_MEMO = "memoNote";
    public static final String MEMO_COLUMN_ID = "memo_id";
    public static final String MEMO_COLUMN_USER_ID = "user_id";
    public static final String MEMO_COLUMN_TIMESTAMP = "note_timestamp";
    public static final String MEMO_COLUMN_TITLE = "note_title";
    public static final String MEMO_COLUMN_CONTENT = "note_content";

    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ROW_NAME + " TEXT, " + ROW_PHONE + " TEXT, " + ROW_EMAIL + " TEXT, " + ROW_PASSWORD + " TEXT)";

        //Query to create memo Table
        String memoQuery = "CREATE TABLE " + TABLE_MEMO + " (" +
                MEMO_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MEMO_COLUMN_USER_ID + " TEXT, " +
                MEMO_COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                MEMO_COLUMN_TITLE + " TEXT, " +
                MEMO_COLUMN_CONTENT + " TEXT);";

        db.execSQL(query);
        db.execSQL(memoQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Insert Data
    public void insertData(ContentValues values){
        db.insert(TABLE_NAME, null, values);
    }

    public boolean checkLoginCredentials(String email, String password) {
        SQLiteDatabase db = getReadableDatabase();
        String selection = ROW_EMAIL + "=?" + " and " + ROW_PASSWORD + "=?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
        boolean loginSuccessful = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return loginSuccessful;
    }

    void updateData(String _id, String name, String phone,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(ROW_NAME,name);
        cv.put(ROW_PHONE,phone);
        cv.put(ROW_PASSWORD,password);

        long result= db.update(TABLE_NAME,cv,"_id=?",new String[]{_id});
        if (result== -1){
            Toast.makeText(context,"FAILED!",Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(context,"SUCCESSFULLY UPDATED!",Toast.LENGTH_SHORT).show();
        }
    }

    public User getUserData(String email) {
        SQLiteDatabase db = getReadableDatabase();
        String selection = ROW_EMAIL + "=?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);

        User user = null;

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ROW_ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ROW_NAME));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ROW_PHONE));
            @SuppressLint("Range") String userEmail = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ROW_EMAIL));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ROW_PASSWORD));

            user = new User(id,name, phone, userEmail, password);
        }


        cursor.close();
        db.close();

        return user;
    }
    //MemoTable Section
    //Insert data for memo Table
    public long insertMemoData(ContentValues values) {

        long  insertStatus = db.insert(TABLE_MEMO, null, values);
        db.close(); // Close the database connection
        return insertStatus; //-1 is failed to insert //for validation
    }
    //Retrieve all data from the MemoTable
    public ArrayList<Memo> getAllMemosByUserId(String userId) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Memo> memos = new ArrayList<>();

        String selection = MEMO_COLUMN_USER_ID + "=?";
        String[] selectionArgs = {userId};

        Cursor cursor = db.query(TABLE_MEMO, null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do { //not sure why needed the  @SuppressLint("Range"), error jumped out then i just  quick fix
                @SuppressLint("Range") String memoId = cursor.getString(cursor.getColumnIndex(MEMO_COLUMN_ID));
                @SuppressLint("Range") String memoTimestamp = cursor.getString(cursor.getColumnIndex(MEMO_COLUMN_TIMESTAMP));
                @SuppressLint("Range") String memoTitle = cursor.getString(cursor.getColumnIndex(MEMO_COLUMN_TITLE));
                @SuppressLint("Range") String memoContent = cursor.getString(cursor.getColumnIndex(MEMO_COLUMN_CONTENT));

                // Create a Memo object and add it to the Memo's ArrayList
                Memo memo = new Memo(memoId, userId, memoTimestamp, memoTitle, memoContent);
                memos.add(memo);
            } while (cursor.moveToNext()); //get all memoNote that from the specific user
        }

        cursor.close();
        db.close();

        return memos;
    }


    // Retrieve a specific memo record based on userId and memoId
    public Memo getMemoByUserIdAndMemoId(String userId, String memoId) {
        SQLiteDatabase db = getReadableDatabase();
        Memo memo = null;

        String selection = MEMO_COLUMN_USER_ID + "=? AND " + MEMO_COLUMN_ID + "=?";
        String[] selectionArgs = {userId, memoId};

        //use to find/point the data
        Cursor cursor = db.query(TABLE_MEMO, null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String memoTimestamp = cursor.getString(cursor.getColumnIndex(MEMO_COLUMN_TIMESTAMP));
            @SuppressLint("Range") String memoTitle = cursor.getString(cursor.getColumnIndex(MEMO_COLUMN_TITLE));
            @SuppressLint("Range") String memoContent = cursor.getString(cursor.getColumnIndex(MEMO_COLUMN_CONTENT));

            // Create a Memo object for the specific row
            // For storing the info
            memo = new Memo(memoId, userId, memoTimestamp, memoTitle, memoContent);
        }

        cursor.close();
        db.close();

        return memo;
    }

    //updateMemo info
    // Add this method to your DatabaseHelper class
    public int updateMemo(String userId, String memoId, String newTitle, String newContent) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MEMO_COLUMN_TITLE, newTitle);
        values.put(MEMO_COLUMN_CONTENT, newContent);

        // Define the condition to update the memo
        // based on user ID and memo ID
        String condition = MEMO_COLUMN_USER_ID + "=? AND " + MEMO_COLUMN_ID + "=?";
        String[] keys = {userId, memoId}; //we use this "composite key" to search row and update

        // Perform the update operation
        int numRowsUpdatedSuccessfully = db.update(TABLE_MEMO, values, condition, keys);

        db.close();

        return numRowsUpdatedSuccessfully;
    }



}

