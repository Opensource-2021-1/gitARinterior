package com.example.main;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class memo extends AppCompatActivity {

    ImageButton writeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo);

        writeButton = (ImageButton) findViewById(R.id.writebutton);
        writeButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(memo.this);
                dlg.setTitle("메모 작성");
                dlg.setMessage("메모를 작성하세요");
                dlg.show();

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), modify.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}