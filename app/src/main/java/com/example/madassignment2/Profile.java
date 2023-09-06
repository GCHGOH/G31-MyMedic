package com.example.madassignment2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {

    Button update_button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        String userId=getIntent().getStringExtra("userId");
        String userName = getIntent().getStringExtra("userName");
        String userPhone = getIntent().getStringExtra("userPhone");
        String userEmail = getIntent().getStringExtra("userEmail");
        String userPassword = getIntent().getStringExtra("userPassword");

        TextView myNameTextView = findViewById(R.id.my_name);
        TextView myEmailTextView = findViewById(R.id.my_email);
        TextView myPhoneTextView = findViewById(R.id.my_phone);
        TextView myPasswordTextView = findViewById(R.id.my_password);

        myNameTextView.setText(userName);
        myEmailTextView.setText(userEmail);
        myPhoneTextView.setText(userPhone);
        myPasswordTextView.setText(userPassword);

        update_button2=findViewById(R.id.btn_modify_info);
        update_button2.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick (View view){
            Intent intent = new Intent(Profile.this, Update_Profile.class);
            intent.putExtra("userId",userId);
            intent.putExtra("userName", userName);
            intent.putExtra("userPhone", userPhone);
            intent.putExtra("userEmail", userEmail);
            intent.putExtra("userPassword", userPassword);
            startActivity(intent);
        }
        });

    }
}