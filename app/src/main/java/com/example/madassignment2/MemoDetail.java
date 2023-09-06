package com.example.madassignment2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MemoDetail extends AppCompatActivity {
    TextView memoTitleTextView;
    TextView createdTimeTextView;
    TextView contentTextView;
    EditText editTitle ;
    EditText editContent ;

    FloatingActionButton editButton;

    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_detail);

        // Initialize your TextViews
        memoTitleTextView = findViewById(R.id.memoTitle3);
        createdTimeTextView = findViewById(R.id.createdTime);
        contentTextView = findViewById(R.id.contentDetail);

        editTitle = findViewById(R.id.editMemoTitle2);
        editContent = findViewById(R.id.editContentDetail);

        // Get the userId and memoId from the Intent
        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        String memoId = intent.getStringExtra("memoId");

        // Use these userId and memoId to retrieve the memo details from the database

        displayMemoDetails(userId, memoId);

        // make it editable
        // Initialize the "Edit" button
        editButton = findViewById(R.id.editContentButton);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEditMode) {
                    // If in edit mode, handle saving the content
                    saveContent();
                } else {
                    // If not in edit mode, switch to edit mode
                    isEditMode = true;
                    updateUI();
                    // Change the button text to "Save"
                    editButton.setImageResource(R.drawable.submit); // Change the icon to a save icon
                }
            }
        });


    }

    // Function to display memo details
    private void displayMemoDetails(String userId, String memoId) {
        DatabaseHelper db = new DatabaseHelper(this);
        Memo memo = db.getMemoByUserIdAndMemoId(userId, memoId);

        if (memo != null) {
            memoTitleTextView.setText(memo.getTitle());
            createdTimeTextView.setText("Created Time: " + memo.getTimestamp());
            contentTextView.setText(memo.getContent());
        } else {
            // Handle the case where the memo is not found
            Toast.makeText(this, "Memo not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI() {
        if (isEditMode) {
            memoTitleTextView.setVisibility(View.GONE);
            contentTextView.setVisibility(View.GONE);
            editTitle.setVisibility(View.VISIBLE);
            editContent.setVisibility(View.VISIBLE);

            // Enable editing in EditText fields
            editTitle.setEnabled(true);
            editContent.setEnabled(true);

            // set the EditText fields with the current values
            editTitle.setText(memoTitleTextView.getText().toString());
            editContent.setText(contentTextView.getText().toString());
        } else {
            memoTitleTextView.setVisibility(View.VISIBLE);
            contentTextView.setVisibility(View.VISIBLE);
            editTitle.setVisibility(View.GONE);
            editContent.setVisibility(View.GONE);

            // Disable editing in EditText fields
            editTitle.setEnabled(false);
            editContent.setEnabled(false);

            // Update the TextView elements with edited values only if you are in edit mode
            if (editButton.getVisibility() == View.VISIBLE) {
                memoTitleTextView.setText(editTitle.getText().toString());
                contentTextView.setText(editContent.getText().toString());
            }
        }
    }


    private void saveContent() {
        // Get the edited title and content from the EditText fields
        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        String memoId = intent.getStringExtra("memoId");

        String editedTitle = editTitle.getText().toString();
        String editedContent = editContent.getText().toString();

        // Check if the title or content has been modified
        DatabaseHelper db = new DatabaseHelper(this);
        Memo originalMemo = db.getMemoByUserIdAndMemoId(userId, memoId);

        if (originalMemo != null) {
            String originalTitle = originalMemo.getTitle();
            String originalContent = originalMemo.getContent();

            if (!editedTitle.equals(originalTitle) || !editedContent.equals(originalContent)) {
                // Update the memo with the edited values
                int updateStatus = db.updateMemo(userId, memoId, editedTitle, editedContent);

                if (updateStatus > 0) {
                    // Notify the user that the content has been saved
                    Toast.makeText(this, "Content saved successfully", Toast.LENGTH_SHORT).show();

                    // Turn off the edit text
                    isEditMode = false;
                    updateUI();
                    editButton.setImageResource(R.drawable.edit);
                } else { //If failed to save

                    Toast.makeText(this, "Failed to save content", Toast.LENGTH_SHORT).show();
                }
            } else {// Content hasn't been modified, no need to save

                isEditMode = false;
                updateUI();
                editButton.setImageResource(R.drawable.edit);
            }
        }
    }

}
