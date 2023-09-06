package com.example.madassignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

//Memo Main Page
//Display memo list
//A button to add new memo (and then naviagte to "AddNewMemo" page to fill inthe detail)

public class MemoPage extends AppCompatActivity {
    private FloatingActionButton addMemo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_page);
        Intent intent = getIntent();
        Intent addNewMemo = new Intent(MemoPage.this,AddNewMemo.class);

        addMemo = findViewById(R.id.addMemoIcon); //this must put after "setContentVIew" , else error
        String userId = intent.getStringExtra("userId");

        LinearLayout memoContainer = findViewById(R.id.memoPageContainer);

        DatabaseHelper db = new DatabaseHelper(this);
        ArrayList<Memo> memos = db.getAllMemosByUserId(userId);

        for (Memo memo : memos) {
            TextView textView = new TextView(this);
            textView.setText(" Title: " + memo.getTitle() + "\n\n" +
                    " Timestamp: " + memo.getTimestamp() + "\n");
            textView.setTextSize(16);

            // Apply styles to the TextView
            textView.setBackgroundResource(R.drawable.memo_textbox_background); // Custom background drawable with an outline
            textView.setElevation(8); // Apply shadow

            // Create LinearLayout.LayoutParams and set margins
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(20, 50, 20, 50);
            textView.setLayoutParams(layoutParams);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MemoPage.this,MemoDetail.class);

                    intent.putExtra("memoId",memo.getMemoId());
                    intent.putExtra("userId",userId);
                    startActivity(intent);

                    Toast.makeText(MemoPage.this, "Clicked on memo: " + memo.getMemoId(), Toast.LENGTH_SHORT).show();
                }
            });

            memoContainer.addView(textView);
        }



        addMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewMemo.putExtra("userId",userId);
                startActivity(addNewMemo);
            }
        });
    }

}