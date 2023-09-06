package com.example.madassignment2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import android.os.Bundle;

public class Consulter extends AppCompatActivity {
    Button btdone;
    EditText et_name,et_address;
    private db_consulter consulter_db;
    TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter);

        btdone = findViewById(R.id.bt_addConsulter);
        et_name = findViewById(R.id.et_consulter);
        et_address = findViewById(R.id.et_address);

        //Examine Result
        tv_result=findViewById(R.id.result_demo);

        consulter_db = new db_consulter(this);
        consulter_db.opentoWrite();

        btdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_name.getText().toString().equals(""))
                {
                    Toast.makeText(Consulter.this,"Please input name of Consulter",Toast.LENGTH_SHORT).show();
                }

                else if(et_address.getText().toString().equals(""))
                {
                    Toast.makeText(Consulter.this,"Please input address for the Consulter",Toast.LENGTH_SHORT).show();
                }

                else
                {
                    consulter_db.insert_consulter(et_name.getText().toString(),et_address.getText().toString());
                    Toast.makeText(Consulter.this,"Done Inputting Data",Toast.LENGTH_SHORT).show();

                }

            }


        });

        //Examine Result
        consulter_db.opentoRead();
        String contentread = consulter_db.queueAll();
        tv_result.setText(contentread);

    }
}