package com.example.sns_project.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sns_project.R;

public class infoActivity extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setToolbarTitle("공지사항");
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cbnubutton:
                    cbnu();
                    break;
                case R.id.swbutton:
                    sw();
                    break;
                case R.id.businessbutton:
                    business();
                    break;
            }
        }
    };

    private void cbnu() {
    // 크롤링 내용
    }

    private void sw() {
    // 크롤링 내용
    }

    private void business() {
    // 크롤링 내용
    }

}