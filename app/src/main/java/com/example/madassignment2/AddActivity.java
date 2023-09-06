package com.example.madassignment2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddActivity extends AppCompatActivity {


    private Spinner spinner;
    EditText medicine_type,name_input,consume_amount_input,extra_note_input;
    Button add_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        String userId=getIntent().getStringExtra("userId");
        String userName = getIntent().getStringExtra("userName");
        String userPhone = getIntent().getStringExtra("userPhone");
        String userEmail = getIntent().getStringExtra("userEmail");
        String userPassword = getIntent().getStringExtra("userPassword");


        medicine_type=findViewById(R.id.MD_Type);
        name_input=findViewById(R.id.MD_Name);
        consume_amount_input=findViewById(R.id.Amount);
        spinner=findViewById(R.id.Consume_Type);
        extra_note_input=findViewById(R.id.Extra_Note);


        add_button=findViewById(R.id.btnAddToMedicine);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedItem = spinner.getSelectedItem().toString();
                //store the data into database
                MedicineDatabase myDB= new MedicineDatabase (AddActivity.this);
                myDB.addmedicine(medicine_type.getText().toString().trim(),name_input.getText().toString().trim(),Integer.valueOf(consume_amount_input.getText().toString().trim()), selectedItem.trim(),extra_note_input.getText().toString().trim());
                DatabaseHelper myDB2=new DatabaseHelper(AddActivity.this);
                User user = myDB2.getUserData(userEmail);
                Intent intent = new Intent(AddActivity.this, MainPage.class);
                intent.putExtra("user", user);  // Pass the user object to MainPage
                startActivity(intent);
            }
        });
    }
}