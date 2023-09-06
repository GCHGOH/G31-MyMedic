package com.example.madassignment2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{


    private Context context;
    Activity activity;
    private ArrayList MD_id,MD_Type,MD_name,consume_amount,consume_type,extra_note;

    Animation translate_anim;

    CustomAdapter(Activity activity, Context context, ArrayList MD_id, ArrayList MD_Type,ArrayList MD_name, ArrayList consume_amount,
                  ArrayList consume_type, ArrayList extra_note){
        this.activity=activity;
        this.context=context;
        this.MD_id=MD_id;
        this.MD_Type=MD_Type;
        this.MD_name=MD_name;
        this.consume_amount=consume_amount;
        this.consume_type=consume_type;
        this.extra_note=extra_note;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.myrow,parent,false);
        return new MyViewHolder(view);

    }

    //Use for updateActivity
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.MD_id_txt.setText(String.valueOf(MD_id.get(position)));
        holder.MD_Type_txt.setText(String.valueOf(MD_Type.get(position)));
        holder.MD_name_txt.setText(String.valueOf(MD_name.get(position)));
        holder.consume_amount_txt.setText(String.valueOf(consume_amount.get(position)));
        holder.consume_type_txt.setText(String.valueOf(consume_type.get(position)));
        holder.extra_note_txt.setText(String.valueOf(extra_note.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context,UpdateAcitivity.class);
                intent.putExtra("id",String.valueOf(MD_id.get(position)));
                intent.putExtra("MD_Type",String.valueOf(MD_Type.get(position)));
                intent.putExtra("MD_Name",String.valueOf(MD_name.get(position)));
                intent.putExtra("Amount",String.valueOf(consume_amount.get(position)));
                intent.putExtra("Consume_Type",String.valueOf(consume_type.get(position)));
                intent.putExtra("Extra_Note",String.valueOf(extra_note.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return MD_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView MD_id_txt,MD_Type_txt,MD_name_txt,consume_amount_txt,consume_type_txt,extra_note_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            MD_id_txt=itemView.findViewById(R.id.MD_ID_txt);
            MD_Type_txt=itemView.findViewById(R.id.MD_Type_txt);
            MD_name_txt=itemView.findViewById(R.id.MD_Name_txt);
            consume_amount_txt=itemView.findViewById(R.id.consume_amount_txt);
            consume_type_txt=itemView.findViewById(R.id.consume_type_txt);
            extra_note_txt=itemView.findViewById(R.id.extra_note_txt);

            mainLayout=itemView.findViewById(R.id.MainLayout);
            //Animate Recyclerview
            translate_anim= AnimationUtils.loadAnimation(context,R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
