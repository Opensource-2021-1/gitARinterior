package com.example.listview_prac;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
    ListView listView;
    //데이터 추가 위치
    String title; //title

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, new ArrayList<String>()));
        new MyTask().execute();
    }

    class MyTask extends AsyncTask<Void, String, String>
    {
        ArrayAdapter<String> adapter;
        @Override
        protected void onPreExecute() { //어댑터 연결
            adapter = (ArrayAdapter<String>)listView.getAdapter();

        }

        @Override
        protected String doInBackground(Void... voids) {  //리스트에 정보 추가
            final StringBuilder builder = new StringBuilder();
            String url1 = "https://software.cbnu.ac.kr/bbs/bbs.php?db=notice";
            String my_link = null;

            try {
                Connection con = Jsoup.connect(url1);
                Document doc = con.get();

                Elements mElementDatas = doc.select("#body_line > nobr ");
                String text = mElementDatas.text();

                int i=0;
                for (Element elem : mElementDatas) {   //제목, 해당 링크
                    String my_title = elem.select("a span b").text();
                    title = my_title;
                    //title.set(i, my_title);
                    publishProgress(title);

                    i++;
                    if(i>15) {
                        break;
                    }
                }
            } catch (IOException e) {
                builder.append("Error");
                System.out.println("에러");
            }

            return "All the datas were added successfully";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            adapter.add(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();

        }
    }
}