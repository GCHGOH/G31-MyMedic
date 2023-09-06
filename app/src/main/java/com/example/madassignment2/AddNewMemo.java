package com.example.madassignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewMemo extends AppCompatActivity {
    Button saveButton;
    EditText memoTitleEditText;
    EditText memoContentEditText;
    DatabaseHelper db;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_memo);

        db = new DatabaseHelper(this);

        // Get the userId from the Intent
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        memoTitleEditText = findViewById(R.id.memoTitle);
        memoContentEditText = findViewById(R.id.memoContent);
        saveButton = findViewById(R.id.saveMemoButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String memoTitle = memoTitleEditText.getText().toString();
                String memoContent = memoContentEditText.getText().toString();

                if (!memoTitle.isEmpty() && !memoContent.isEmpty()) {
                    // Create a ContentValues object (database feature) to insert memo data
                    ContentValues memoData = new ContentValues();
                    memoData.put(DatabaseHelper.MEMO_COLUMN_USER_ID, userId);
                    memoData.put(DatabaseHelper.MEMO_COLUMN_TITLE, memoTitle);
                    memoData.put(DatabaseHelper.MEMO_COLUMN_CONTENT, memoContent);

                    // Insert the memo data into the database
                    //result = -1 , is faield
                    //else result will be the row id of the table in the db
                    long result = db.insertMemoData(memoData);

                    //for validation
                    if (result != -1) {
                        Toast.makeText(AddNewMemo.this, "Memo saved successfully", Toast.LENGTH_SHORT).show();
                        // Finish this activity and return to the previous activity
                        Intent finish = new Intent(AddNewMemo.this,MemoPage.class);
                        finish.putExtra("userId",userId);
                        startActivity(finish);
                    } else {
                        Toast.makeText(AddNewMemo.this, "Failed to save memo", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddNewMemo.this, "Please enter both title and content", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
