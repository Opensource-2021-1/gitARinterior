package com.example.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class select extends AppCompatActivity {

    Button infoButton, pageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);

        infoButton = (Button) findViewById(R.id.mypagebutton);
        infoButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), information.class);
                startActivity(intent);
            }
        });

        pageButton = (Button) findViewById(R.id.mypagebutton);
        pageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), my_page.class);
                startActivity(intent);
            }
        });
    }

}