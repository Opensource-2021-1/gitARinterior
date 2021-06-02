package com.cookandroid.sw7up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //핸들러로 ui조작
    class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                //어댑터 타이틀 갱신
                ArrayAdapter adapter = (ArrayAdapter) sw7up_listView.getAdapter(); //어댑터 불러오기
                adapter.notifyDataSetChanged(); //불러온 후 갱신
            }
        }
    }

    UIHandler handler = new UIHandler();
    ListView sw7up_listView;
    ArrayList<String> title_list = new ArrayList<String>();  //제목들
    ArrayList<String> url_list = new ArrayList<String>(); //url 넣기
    ArrayAdapter<String> sw7up_adapter;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView)findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true); //js 활성화
        webView.getSettings().setDomStorageEnabled(true); //js 로드
        webView.setWebChromeClient(new WebChromeClient());  //js 활성화 필요
        webView.setWebViewClient(new WebViewClient());  //js
        webView.addJavascriptInterface(new JSInterface(), "Android"); //js 접근

        sw7up_listView = (ListView) findViewById(R.id.sw7up_listview);
        sw7up_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, title_list);
        sw7up_listView.setAdapter(sw7up_adapter);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {  // 페이지가 모두 로드되었을 때, 작업 정의
                super.onPageFinished(view, url);
                //웹뷰로 접속해서 getHtml 실행
                view.loadUrl("javascript:window.Android.getHtml(document.getElementsByTagName('html')[0].innerHTML);");
            }
        });
        webView.loadUrl("https://sw7up.cbnu.ac.kr/community/notice?page=1&limit=10&sort=-createdAt"); //url 웹뷰에 접근
//        webView.loadUrl("https://sw7up.cbnu.ac.kr/community/notice?page=2&limit=10&sort=-createdAt"); //url 웹뷰에 접근

        sw7up_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        Sw7upNoticeDetail.class
                ); //넘어갈 화면

                //인텐트에 데이터 전송.
                intent.putExtra("url", url_list.get(position)); //누른 버튼에 해당하는 링크 전송
                startActivity(intent);
            }
        });

    }

    public class JSInterface {
        @JavascriptInterface
        public void getHtml(String html) {
            //위 자바스크립트가 호출되면 여기로 html이 반환됨
            Document doc = Jsoup.parse(html);

            Elements cbnu_subject = doc.select("div.card"); //공지사항 제목 단락

            //제목, 링크
            for (Element elem : cbnu_subject) {
                String sw7up_title = elem.select(".mb-0").text(); //제목
                /* 공지사항 제목을 리스트에 add */
                title_list.add(sw7up_title);  //리스트에 타이틀 업데이트

                /* 링크를 리스트에 add */
//                String sw7up_link = elem.select("a").attr("href");  //링크 크롤링
                String sw7up_link = elem.attr("onclick");
                url_list.add(sw7up_link);
            }
            //핸들러로 리스트에 add된 제목을 갱신
            Message msg = Message.obtain();
            handler.sendEmptyMessage(0);
        }
    }
}
