package com.example.madassignment2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Update_Profile extends AppCompatActivity {

    EditText my_name_input,my_phone_input, my_password_input,my_email_input;
    Button update_button2;
    String my_id,my_name,my_email2,my_phone,my_password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);



        my_name_input=findViewById(R.id.my_name2);
        my_phone_input=findViewById(R.id.my_phone2);
        my_password_input=findViewById(R.id.my_password2);
        update_button2=findViewById(R.id.update_button2);

        //First we call this
        getAndSetIntentData();

        //set action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(my_name);
        }


        update_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper myDB=new DatabaseHelper(Update_Profile.this);
                my_name=my_name_input.getText().toString().trim();
                my_phone=my_phone_input.getText().toString().trim();
                my_password=my_password_input.getText().toString().trim();
                myDB.updateData(my_id,my_name,my_phone,my_password);

                User user = myDB.getUserData(my_email2);
                Intent intent = new Intent(Update_Profile.this, MainPage.class);
                intent.putExtra("user", user);  // Pass the user object to MainPage
                startActivity(intent);
            }
        });


    }
    void getAndSetIntentData(){
        if(getIntent().hasExtra("userId") && getIntent().hasExtra("userName")&&getIntent().hasExtra("userPhone")&& getIntent().hasExtra("userEmail")&&getIntent().hasExtra("userPassword")){
            //Getting Data from Intent
            my_id=getIntent().getStringExtra("userId");
            my_name=getIntent().getStringExtra("userName");
            my_email2=getIntent().getStringExtra("userEmail");
            my_phone=getIntent().getStringExtra("userPhone");
            my_password=getIntent().getStringExtra("userPassword");


            //Setting Intent Data
            my_name_input.setText(my_name);
            my_phone_input.setText(my_phone);
            my_password_input.setText(my_password);


        }else{
            Toast.makeText(this,"No data.",Toast.LENGTH_SHORT).show();
        }
    }
}