package com.cookandroid.ccccc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView weather_tv;
    String contents = ""; //크롤링 내용

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weather_tv = findViewById(R.id.weather_tv);
        weather_tv.setMovementMethod(new ScrollingMovementMethod()); //스크롤 가능한 텍스트뷰로 만들기

        Button goButton = (Button) findViewById(R.id.button);
        goButton.setOnClickListener(new View.OnClickListener() { //버튼 클릭 시 크롤링
            @Override
            public void onClick(View view) {
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();
            }
        });
        //new crawling().main(weather_tv);
    }

    //Params, Progress, Result
    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> { //파라미터, 진행상태값, 최종결과
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) { //가변인자
            final StringBuilder builder = new StringBuilder();
            String url1 = "https://software.cbnu.ac.kr/bbs/bbs.php?db=notice";
            String my_link = null;

            try {
                Connection con = Jsoup.connect(url1);
                Document doc = con.get();
                // doc.outputSettings(new Document.OutputSettings().prettyPrint(false));

                Elements mElementDatas = doc.select("#body_line > nobr ");
                String text = mElementDatas.text();

                for (Element elem : mElementDatas) {   //제목, 해당 링크
                    String my_title = elem.select("a span b").text();
                    //text는 url 사이의 글자만 긁어옴 /전체는 toString();
                    my_link = elem.select("a").attr("href");
                    String ftext = my_title;

                    System.out.println(ftext);


                    //글 선택 후 내부 내용 크롤링
                    String url2 = my_link;
                    Connection con2 = Jsoup.connect("https://software.cbnu.ac.kr" + url2);
                    Document doc2 = con2.get();
                    //doc2.outputSettings(new Document.OutputSettings().prettyPrint(false));

                    Elements EDatas = doc2.select("#articles"); //articles에서 따옴

                    for (Element elem2 : EDatas) {
                        //span
                        String content1 = elem2.select("span").text();
                        String content2 = elem2.select("p").text();
                        System.out.println(content1 + content2);

                        //이미지 크롤링
                        String img_link = elem2.select("#articles div img").attr("src");

                        //URL img_receive = new URL(img_link);
                        //FileOutputStream out = new FileOutputStream("C");
                        //BufferedImage img = null;

                        //img = ImageIO.read(img_receive);

                        System.out.println(img_link);

                        System.out.println("-------------------------------------------------------------");
                        contents += ftext + "\n" + content1 + "\n" + content2 + "\n" + img_link; //내용 합치기
                        System.out.println("-------------------------------------------------------------");
                    }
                }
            } catch (IOException e) {
                builder.append("Error");
                System.out.println("에러");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            weather_tv.setText(contents); //크롤링 내용 textview로 보기
        }
    }
}

//class crawling extends Activity {
//    public crawling() {
//    }
//
//    public void main(TextView weather_tv) {
//        // TODO Auto-generated method stub
//
//        final StringBuilder builder = new StringBuilder();
//        String url1 = "https://software.cbnu.ac.kr/bbs/bbs.php?db=notice";
//        String my_link = null;
//
//        try{
//            Connection con = Jsoup.connect(url1);
//            Document doc = con.get();
//            // doc.outputSettings(new Document.OutputSettings().prettyPrint(false));
//
//            Elements mElementDatas = doc.select("#body_line > nobr ");
//            String text = mElementDatas.text();
//
//            for(Element elem : mElementDatas) {   //제목, 해당 링크
//                String my_title = elem.select("a span b").text();
//                //text는 url 사이의 글자만 긁어옴 /전체는 toString();
//                my_link = elem.select("a").attr("href");
//                String ftext = my_title;
//
//                /*System.out.println(ftext);*/
//
//
//
//                //글 선택 후 내부 내용 크롤링
//                String url2 = my_link;
//                Connection con2 = Jsoup.connect("https://software.cbnu.ac.kr"+url2);
//                Document doc2 = con2.get();
//                //doc2.outputSettings(new Document.OutputSettings().prettyPrint(false));
//
//                Elements EDatas = doc2.select("#articles"); //articles에서 따옴
//
//                for(Element elem2 : EDatas) {
//                    //span
//                    String content1 = elem2.select("span").text();
//                    String content2 = elem2.select("p").text();
//                    /*System.out.println(content1+content2);*/
//
//                    //이미지 크롤링
//                    String img_link = elem2.select("#articles div img").attr("src");
//
//                    //URL img_receive = new URL(img_link);
//                    //FileOutputStream out = new FileOutputStream("C");
//                    //BufferedImage img = null;
//
//                    //img = ImageIO.read(img_receive);
//
//                    /*System.out.println(img_link);*/
//                    String sum = ftext+"\n"+content1+"\n"+content2+"\n"+img_link;
//                    weather_tv.setText(sum);
//                }
//            }
//        }catch(IOException e) {
//            builder.append("Error");
//            System.out.println("에러");
//        }
//
//    }
//
//}