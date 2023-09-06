    package com.example.madassignment2;

    import android.content.DialogInterface;
    import android.os.Bundle;
    import androidx.appcompat.app.ActionBar;
    import androidx.appcompat.app.AlertDialog;
    import androidx.appcompat.app.AppCompatActivity;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Toast;



    public class UpdateAcitivity extends AppCompatActivity {

        EditText medicine_type_input,medicine_input,consume_amount_input,consume_type_input, extra_note_input;
        Button update_button, delete_button;
        String id,md_type,name,amount,type,note;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_update);

            medicine_type_input=findViewById(R.id.MD_Type2);
            medicine_input=findViewById(R.id.MD_name2);
            consume_amount_input=findViewById(R.id.consume_amount2);
            consume_type_input=findViewById(R.id.consume_type2);
            extra_note_input=findViewById(R.id.extra_note2);
            update_button=findViewById(R.id.update_button);
            delete_button=findViewById(R.id.delete_button);
            //First we call this
            getAndSetIntentData();

            //set action bar
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(name);
            }


            update_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MedicineDatabase myDB=new MedicineDatabase(UpdateAcitivity.this);
                    md_type=medicine_type_input.getText().toString().trim();
                    name=medicine_input.getText().toString().trim();
                    amount=consume_amount_input.getText().toString().trim();
                    type=consume_type_input.getText().toString().trim();
                    note=extra_note_input.getText().toString().trim();
                    myDB.updateData(id,md_type,name,amount,type,note);
                }
            });

            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmDialog();
                }
            });

        }
        void getAndSetIntentData(){
            if(getIntent().hasExtra("id") && getIntent().hasExtra("MD_Type")&&getIntent().hasExtra("MD_Name")&& getIntent().hasExtra("Amount")&& getIntent().hasExtra("Consume_Type")&& getIntent().hasExtra("Extra_Note")){
                //Getting Data from Intent
                id=getIntent().getStringExtra("id");
                md_type=getIntent().getStringExtra("MD_Type");
                name=getIntent().getStringExtra("MD_Name");
                amount=getIntent().getStringExtra("Amount");
                type=getIntent().getStringExtra("Consume_Type");
                note=getIntent().getStringExtra("Extra_Note");


                //Setting Intent Data
                medicine_type_input.setText(md_type);
                medicine_input.setText(name);
                consume_amount_input.setText(amount);
                consume_type_input.setText(type);
                extra_note_input.setText(note);

            }else{
                Toast.makeText(this,"No data.",Toast.LENGTH_SHORT).show();
            }
        }
        void confirmDialog(){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Delete "+name+ " ?");
            builder.setMessage("Are you sure you want to delete "+ name + " ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    MedicineDatabase myDB=new MedicineDatabase(UpdateAcitivity.this);
                    myDB.deleteOneRow(id);
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
        }
    }