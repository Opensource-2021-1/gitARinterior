package com.cookandroid.sw7up;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Sw7upNoticeDetail extends Activity {
    //핸들러로 ui조작
    class TextHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                sw7up_contents.setText((String) msg.obj);
            }else if (msg.what==1){  //이미지 로드
                Glide.with(getApplicationContext()).load(img_link).override(250, 450).into(cbnu_detail_image); //glide라이브러리로 이미지 로드
            }
        }
    }

    TextView sw7up_contents;
    ImageView cbnu_detail_image;
    TextHandler handler = new TextHandler();  //핸들러
    String url = ""; //다음 내용 링크
    String img_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sw7up_notice_detail);

        sw7up_contents = (TextView) findViewById(R.id.textView1);  //공지 내용
        cbnu_detail_image = (ImageView) findViewById(R.id.content_image); //공지 이미지
        sw7up_contents.setMovementMethod(new ScrollingMovementMethod()); //스크롤 가능

        ContentRunnable cr = new ContentRunnable();
        Thread t = new Thread(cr);
//        t.setDaemon(true); // 메인스레드와 함께 종료
        t.start();  //공지 크롤링 시작
    }

    class ContentRunnable implements Runnable {  //공지사항 내용 띄우기
        ContentRunnable() {
        }

        /* 공지 크롤링중 ... */
        @Override
        public void run() {

            Document doc2 = null;
            String contents = "";
            try {
                Intent intent = getIntent(); // 보내온 Intent를 얻는다
                doc2 = Jsoup.connect("https://sw7up.cbnu.ac.kr/community/notice" + intent.getStringExtra("url")).get();

//                doc2.outputSettings(new Document.OutputSettings().prettyPrint(false));
                doc2.select("br").append("\\n");
                doc2.select("p").prepend("\\n");
                doc2.select("div").prepend("\\n");

                Elements cbnuContent = doc2.select(".brdcon"); //공지사항 내부 전체
//                for (Element elem2 : cbnuContent) {
//                    String content1 = elem2.select("span").text();String content1 = cbnuContent.select("span").text();
//                String content2 = elem2.select("p").text();
                contents = cbnuContent.select("p").text(); //p만 긁어와도 충분...

                //이미지 크롤링
                img_link = cbnuContent.select("img").attr("src");
                //줄 띄우기
                String s = contents.replaceAll("\\\\n", "\n");
                contents = Jsoup.clean(s, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            /* 공지 크롤링 끝 ...  */
            handler.sendEmptyMessage(1); //이미지 띄우기
            //핸들러를 통해서 공지 내용 띄우기
            Message msg = Message.obtain(handler, 0, contents);
            handler.sendMessage(msg);
        }
    }

}
