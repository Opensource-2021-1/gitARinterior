package com.example.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class modify extends AppCompatActivity {

    Button modifycheckButton, modifycancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify);

        modifycheckButton = (Button) findViewById(R.id.modifycheckbutton);
        modifycheckButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "수정 완료", Toast.LENGTH_SHORT).show();
            }
        });

        modifycancelButton = (Button) findViewById(R.id.modifycheckbutton);
        modifycancelButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "수정 취소", Toast.LENGTH_SHORT).show();
            }
        });
    }

}