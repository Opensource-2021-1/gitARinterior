
/***
 (C) 2021. (2018038032 김예원)
 ***/

package com.example.listview_prac;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class Clicked extends Activity {
    TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clicked);

        Intent intent = getIntent();
        textView = (TextView)findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());

        Bundle bundle = getIntent().getExtras();
        String text = bundle.getString("arr_text");

        textView.setText(text);

    }

}
