package com.example.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class information extends AppCompatActivity {

    Button cbnuButton, swButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);

        cbnuButton = (Button) findViewById(R.id.cbnubutton);
        cbnuButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), cbnu.class);
                startActivity(intent);
            }
        });

        swButton = (Button) findViewById(R.id.swbutton);
        swButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), sw.class);
                startActivity(intent);
            }
        });
    }

}