package com.example.madassignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabItem;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);



        String userId ;
        String userName ;
        String userPhone ;
        String userEmail ;
        String userPassword ;

        User user = (User) getIntent().getSerializableExtra("user");
        if (user != null) {
            userId = user.getId();
            userName = user.getName();
            userPhone = user.getPhone();
            userEmail = user.getEmail();
            userPassword = user.getPassword();
        }
        else{
            userId=null ;
            userName=null ;
            userPhone=null ;
            userEmail=null ;
            userPassword=null ;
        }

        LinearLayout home_button=findViewById(R.id.Logout);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, Login.class);
                startActivity(intent);
            }
        });


        LinearLayout profile_button=findViewById(R.id.Profile);
        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, Profile.class);
                intent.putExtra("userId",userId);
                intent.putExtra("userName", userName);
                intent.putExtra("userPhone", userPhone);
                intent.putExtra("userEmail", userEmail);
                intent.putExtra("userPassword", userPassword);
                startActivity(intent);
            }
        });

        LinearLayout choiceLayout=findViewById(R.id.choice1);
        choiceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, medicine.class);
                intent.putExtra("userId",userId);
                intent.putExtra("userName", userName);
                intent.putExtra("userPhone", userPhone);
                intent.putExtra("userEmail", userEmail);
                intent.putExtra("userPassword", userPassword);
                startActivity(intent);
            }
        });

        LinearLayout choiceLayout2=findViewById(R.id.choice2);
        choiceLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, Medicine_Reminder.class);
                startActivity(intent);
            }
        });

        LinearLayout choiceLayout3=findViewById(R.id.choice3);
        choiceLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, Consulter.class);
                startActivity(intent);
            }
        });

        LinearLayout choiceLayout4=findViewById(R.id.choice4);
        choiceLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, MemoPage.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });

        LinearLayout choiceLayout5=findViewById(R.id.choice5);
        choiceLayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, NearbyPharmacy.class);
                startActivity(intent);
            }
        });

        LinearLayout choiceLayout6=findViewById(R.id.choice6);
        choiceLayout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, ExportData.class);
                intent.putExtra("userId",userId);
                intent.putExtra("userName", userName);
                intent.putExtra("userPhone", userPhone);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });
    }
}