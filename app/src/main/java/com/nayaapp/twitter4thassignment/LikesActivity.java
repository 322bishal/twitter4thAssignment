package com.nayaapp.twitter4thassignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nayaapp.twitter4thassignment.adapter.InterestsAdapter;
import com.nayaapp.twitter4thassignment.model.DataSet;

import java.util.ArrayList;
import java.util.List;

public class LikesActivity extends AppCompatActivity {
RecyclerView recyclerView;
Button btn_next;
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_interested );
        recyclerView=findViewById( R.id.interestsRv );
        btn_next=findViewById( R.id.inte_next );
        textView=findViewById( R.id.inte_tv );

        List<DataSet> interestsList = new ArrayList<>();

        interestsList.add( new DataSet("Travelling", "Nepal", "France", "Bali","Greenland","Greenland"));
        interestsList.add( new DataSet("Music", "RNB", "Trance", "Trap","Alternative rock","Heavymetal"));
        InterestsAdapter interestsAdapter = new InterestsAdapter(this, interestsList);
        recyclerView.setAdapter( interestsAdapter );
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        textView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTO();
            }
        } );
        btn_next.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTO();
            }
        } );

    }
    void NextTO(){
        Intent intent = new Intent( LikesActivity.this,Dashboard.class );
        startActivity( intent );
    }
}
