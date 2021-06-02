package com.cookandroid.crawl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //핸들러로 ui조작
    class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                //어댑터 타이틀 갱신
                ArrayAdapter adapter = (ArrayAdapter) listView.getAdapter(); //어댑터 불러오기
                adapter.notifyDataSetChanged(); //불러온 후 갱신
            }
        }
    }

    UIHandler handler = new UIHandler();  //핸들러
    ListView listView;  //리스트뷰
    ArrayList<String> title_list = new ArrayList<String>();  //제목들
    ArrayList<String> url_list = new ArrayList<String>(); //url 넣기
    ArrayAdapter<String> cbnu_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview); //리스트뷰

        //어댑터 생성
        cbnu_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, title_list);
        listView.setAdapter(cbnu_adapter);

        //스레드의 Runnable로 구현, 리스트 띄우기
        TitleRunnable nr = new TitleRunnable();
        Thread t = new Thread(nr);
        t.setDaemon(true); // 메인스레드와 함께 종료
        t.start();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        NoticeDetail.class
                ); //넘어갈 화면

                //인텐트에 데이터 전송.
                intent.putExtra("url", url_list.get(position)); //누른 버튼에 해당하는 링크 전송
                startActivity(intent);
            }
        });
    }

    class TitleRunnable implements Runnable {  //runnalbe로 thread 상속 안하고 run()만 쓰기
        TitleRunnable() {
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
                    String cbnuTitle = elem.select("td a").text(); //제목

                    /* 공지사항 제목을 리스트에 add */
                    title_list.add(cbnuTitle);  //리스트에 타이틀 업데이트

                    /* 링크를 리스트에 add */
                    String cbnuLink = elem.select("a").attr("href");  //링크 크롤링
                    url_list.add(cbnuLink);
                }
                //핸들러로 리스트에 add된 제목을 갱신
                Message msg = Message.obtain();
                handler.sendEmptyMessage(0);
            } catch (IOException e) {
                builder.append("Error");
                System.out.println("에러");
            }
        }
    }
}

//class MyAdapter extends BaseAdapter {
//    Context context;
//    int layout;
//    ArrayList<MyList> title_list;
//    LayoutInflater inf;
//
//    public MyAdapter(Context context, int layout, ArrayList<MyList> title_list) {
//        this.context = context;
//        this.layout = layout;
//        this.title_list = title_list;
//        inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    @Override
//    public int getCount() {
//        return title_list.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return title_list.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        if (view == null) {
//            view = inf.inflate(layout, null);
//        }
//        ImageView img = (ImageView) view.findViewById(R.id.imageView1);
//        TextView title = (TextView) view.findViewById(R.id.textView1);
//
//        MyList mylist = title_list.get(i);
//
//        img.setImageResource(mylist.img);
//        title.setText(mylist.title);
//
//        return view;
//    }
//}
//
//class MyList {
//    String title = "";  //제목
//    int img;  //별 이미지
//
//    public MyList(String title, int img) {
//        super();
//        this.title = title;
//        this.img = img;
//    }
//
//    public MyList() {
//    }
//}