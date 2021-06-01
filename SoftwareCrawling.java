
/***
 (C) 2021. 2018038032 김예원
 ***/

package com.example.listview_prac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class SoftwareCrawling extends AppCompatActivity {
    ListView listView;
    String title; //title
    Vector<String> content_arr = new Vector<String>(15);

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
            String contents="";

            try {
                Connection con = Jsoup.connect(url1);
                Document doc = con.get();

                Elements mElementDatas = doc.select("#body_line > nobr ");
                String text = mElementDatas.text();

                int i = 0;
                for (Element elem : mElementDatas) {   //제목, 해당 링크
                    String my_title = elem.select("a span b").text();
                    title = my_title;
                    publishProgress(title);

                    //내부 내용 크롤링, 내부 내용으로 이어지는 link 추출
                    my_link = elem.select("a").attr("href");
                    String url2 = my_link;
                    Connection con2 = Jsoup.connect("https://software.cbnu.ac.kr" + url2);
                    Document doc2 = con2.get();

                    doc2.select("br").append("\\n");
                    doc2.select("p").prepend("\\n");
                    doc2.select("div").prepend("\\n");
                    if(i==0) doc2.select("span").prepend("\\n");


                    Elements EDatas = doc2.select("#articles");    // articles에서 따옴
                    String[] result;
                    for (Element elem2 : EDatas) {
                        //span, p
                        String content1 = elem2.select("p").text();
                        String content2 = elem2.select("span").text();
                        //String content3 = elem2.select("table").text();

                        String content =content1;
                        if(i==0) {
                            content = content2;
                        }
                        String total = content.replaceAll("\\\\n", "\n");
                        content_arr.add(Jsoup.clean(total,"", Whitelist.none(), new Document.OutputSettings().prettyPrint(false)));

                    }
                    i++;
                    if (i > 15) {
                        break;
                    }
                }
            }catch (IOException e) {
                builder.append("Error");
            }

            return "All the datas were added successfully";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            adapter.add(values[0]);

        }

        @Override
        protected void onPostExecute(String result) {

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Toast.makeText(SoftwareCrawling.this, "Clicked! ", Toast.LENGTH_SHORT).show();

                    //String c_list = content_arr.get(position);  //내가 선택한 리스트 내용 담당
                    Intent intent = new Intent(SoftwareCrawling.this, Clicked.class);
                    intent.putExtra("arr_text", content_arr.get(position));
                    startActivity(intent);

                }
            });


        }
    }
}