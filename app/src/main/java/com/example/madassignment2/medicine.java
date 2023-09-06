package com.example.madassignment2;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class medicine extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_imageview;
    TextView no_data;


    MedicineDatabase myDB;
    ArrayList<String> MD_id, MD_Type,MD_name, consume_amount,consume_type,extra_note;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicinelist);

        String userId=getIntent().getStringExtra("userId");
        String userName = getIntent().getStringExtra("userName");
        String userPhone = getIntent().getStringExtra("userPhone");
        String userEmail = getIntent().getStringExtra("userEmail");
        String userPassword = getIntent().getStringExtra("userPassword");

        recyclerView=findViewById(R.id.recyclerView);
        add_button=findViewById(R.id.add_button);
        empty_imageview=findViewById(R.id.empty_imageview);
        no_data=findViewById(R.id.no_data);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(medicine.this, AddActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("userName", userName);
                intent.putExtra("userPhone", userPhone);
                intent.putExtra("userEmail", userEmail);
                intent.putExtra("userPassword", userPassword);
                startActivity(intent);
            }
        });
        myDB=new MedicineDatabase(medicine.this);
        MD_id=new ArrayList<>();
        MD_Type=new ArrayList<>();
        MD_name=new ArrayList<>();
        consume_amount=new ArrayList<>();
        consume_type=new ArrayList<>();
        extra_note=new ArrayList<>();

        storeDataInArrays();

        customAdapter=new CustomAdapter(medicine.this,this,MD_id,MD_Type,MD_name,consume_amount,consume_type,extra_note);

        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(medicine.this));


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor=myDB.readAllData();
        if (cursor.getCount()==0){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            while(cursor.moveToNext()){
                MD_id.add(cursor.getString(0));
                MD_Type.add(cursor.getString(1));
                MD_name.add(cursor.getString(2));
                consume_amount.add(cursor.getString(3));
                consume_type.add(cursor.getString(4));
                extra_note.add(cursor.getString(5));
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.delete_all){
            confirmDialog();
        }

        return super.onOptionsItemSelected(item);
    }
    void confirmDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MedicineDatabase myDB=new MedicineDatabase(medicine.this);
                myDB.deleteAllData();
                //Refresh Activity
                Intent intent=new Intent(medicine.this,medicine.class);
                startActivity(intent);
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