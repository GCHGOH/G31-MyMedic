package com.example.madassignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import android.widget.Toast;

public class MedicineDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME="Medicine.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME="my_medic";
    private static final String COLUMN_ID="_id";
    private static final String COLUMN_MD_TYPE="medicine_type";
    private static final String COLUMN_NAME="medicine_name";
    private static final String COLUMN_AMOUNT="consume_amount";
    private static final String COLUMN_TYPE="consume_type";
    private static final String COLUMN_NOTE="extra_note";

    MedicineDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }
    //Create medicine database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MD_TYPE + " TEXT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_AMOUNT + " INTEGER, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_NOTE + " TEXT " +
                ")";
        db.execSQL(query);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    void addmedicine(String medicine_type,String name, int amount, String type,String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_MD_TYPE,medicine_type);
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_AMOUNT,amount);
        cv.put(COLUMN_TYPE,type);
        cv.put(COLUMN_NOTE,note);
        long result=db.insert(TABLE_NAME,null,cv);
        if (result == -1){
            Toast.makeText(context,"FAILED",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context,"ADDED SUCCESSFULLY",Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query="SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=null;
        if (db!=null){
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }
    void updateData(String row_id, String medicine_type,String name, String amount, String type,String note){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(COLUMN_MD_TYPE,medicine_type);
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_AMOUNT,amount);
        cv.put(COLUMN_TYPE,type);
        cv.put(COLUMN_NOTE,note);

        long result= db.update(TABLE_NAME,cv,"_id=?",new String[]{row_id});
        if (result== -1){
            Toast.makeText(context,"FAILED!",Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(context,"SUCCESFULLY UPDATED!",Toast.LENGTH_SHORT).show();
        }
    }
    void deleteOneRow(String row_id){
        SQLiteDatabase db=this.getWritableDatabase();
        long result=db.delete(TABLE_NAME,"_id=?",new String[]{row_id});
        if (result== -1){
            Toast.makeText(context,"FAILED!",Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(context,"SUCCESSFULLY DELETED!",Toast.LENGTH_SHORT).show();
        }
    }
    void deleteAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME);
    }
}
