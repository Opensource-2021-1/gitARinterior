package com.cookandroid.crawl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    //핸들러로 ui조작
    class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {  //텍스트뷰 조작
                // 메인 스레드 ui변경
                String contents = (String) msg.obj;
                goText.setText(contents);
            }
        }
    }

    UIHandler handler = new UIHandler();

    TextView goText;
    String source;
    String contents = ""; //크롤링 내용

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goText = findViewById(R.id.goText);
        goText.setMovementMethod(new ScrollingMovementMethod()); //스크롤 가능한 텍스트뷰로 만들기
        Button goButton = (Button) findViewById(R.id.button); //버튼

        goButton.setOnClickListener(new View.OnClickListener() { //버튼 클릭 시 크롤링
            @Override
            public void onClick(View view) {
                //스레드의 Runnable로 구현
                NewRunnable nr = new NewRunnable();
                Thread t = new Thread(nr);
                t.setDaemon(true); // 메인스레드와 함께 종료
                t.start();
            }
        });
    }
    class NewRunnable implements Runnable {  //runnalbe로 thread 상속 안하고 run()만 쓰기
        NewRunnable() {
        }

        @Override
        public void run() {
            //크롤링
            final StringBuilder builder = new StringBuilder();
            String url1 = "https://www.chungbuk.ac.kr/site/www/boardList.do?boardSeq=112&key=698";
            String my_link = null;
            try {
                Connection con = Jsoup.connect(url1);
                Document doc = con.get();
                Elements cbnuSubject = doc.select("td.subject"); //공지사항 제목 단락

                //제목, 링크
                for (Element elem : cbnuSubject) {
                    String cbnuTitle = elem.select("td a span").text(); //제목
                    String cbnuLink = elem.select("td a").attr("href");

                    //글 선택 후 내부 내용 크롤링
                    String url2 = cbnuLink;
                    Document doc2 = Jsoup.connect("https://www.chungbuk.ac.kr/site/www/" + url2).get();

                    Elements cbnuContent = doc2.select(".brdcon"); //공지사항 내부 전체
                    for (Element elem2 : cbnuContent) {
                        String content1 = elem2.select("span").text();
                        String content2 = elem2.select("p").text();

                        String img_link = elem2.select("#articles div img").attr("src");


                        contents += cbnuTitle + "\n" + content1 + "\n" + content2 + "\n" + img_link + "\n\n";
                    }
                }
                Message msg = Message.obtain();
                msg.obj = contents;
                msg.what=0;
                handler.sendMessage(msg); //메인스레드 핸들러로 메세지 전달
            } catch (IOException e) {
                builder.append("Error");
                System.out.println("에러");
            }
        }
    }
}

//Params, Progress, Result
//    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> { //파라미터, 진행상태값, 최종결과
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) { //가변인자
//            final StringBuilder builder = new StringBuilder();
//            String url1 = "https://www.chungbuk.ac.kr/site/www/boardList.do?boardSeq=112&key=698";
//            String my_link = null;
//            try {
//                Document doc = Jsoup.connect(url1).get();
//                Elements cbnuSubject = doc.select("td.subject"); //공지사항 제목 단락
//
//                //제목, 링크
//                for (Element elem : cbnuSubject) {
//                    String cbnuTitle = elem.select("td a span").text(); //제목
//                    String cbnuLink = elem.select("a").attr("href");
//
//                    //글 선택 후 내부 내용 크롤링
//                    String url2 = cbnuLink;
//                    Document doc2 = Jsoup.connect("https://www.chungbuk.ac.kr/site/www/" + url2).get();
//
//                    Elements cbnuContent = doc.select(".brdcon"); //공지사항 내부 전체
//                    for (Element elem2 : cbnuContent) {
//                        String content1 = elem2.select("span").text();
//                        String content2 = elem2.select("p").text();
//
//                        String img_link = elem2.select("#articles div img").attr("src");
//
//
//                        contents += cbnuTitle + "\n" + content1 + "\n" + content2 + "\n" + img_link;
//                    }
//                }
//            } catch (IOException e) {
//                builder.append("Error");
//                System.out.println("에러");
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            goText.setText(contents); //크롤링 내용 textview로 보기
//        }
//    }
//}