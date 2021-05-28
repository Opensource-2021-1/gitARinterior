package com.example.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class my_page extends AppCompatActivity {

    Button modifyButton, bookmarkButton, memoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_page);

        modifyButton = (Button) findViewById(R.id.modifybutton);
        modifyButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(my_page.this);
                dlg.setTitle("비밀번호 확인");
                dlg.setMessage("비밀번호를 입력하세요");
                dlg.show();

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), modify.class);
                        startActivity(intent);
                    }
                });
            }
        });

        bookmarkButton = (Button) findViewById(R.id.bookmarkbutton);
        bookmarkButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), bookmark.class);
                startActivity(intent);
            }
        });

        memoButton = (Button) findViewById(R.id.memobutton);
        memoButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), memo.class);
                startActivity(intent);
            }
        });


    }

}