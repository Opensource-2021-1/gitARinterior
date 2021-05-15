package square;

import org.jsoup.Jsoup;
import org.jsoup.*;
import org.jsoup.nodes.Document;	/////
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Connection;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Crawling {
	public Crawling() { 
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		final StringBuilder builder = new StringBuilder();
        String url1 = "https://software.cbnu.ac.kr/bbs/bbs.php?db=notice";
        String my_link = null;
        
		try{
			Connection con = Jsoup.connect(url1);
            Document doc = con.get();
           // doc.outputSettings(new Document.OutputSettings().prettyPrint(false));

            Elements mElementDatas = doc.select("#body_line > nobr ");
            String text = mElementDatas.text();
            
            int i=0;
	            for(Element elem : mElementDatas) {	//제목, 해당 링크
		            	
		                String my_title = elem.select("a span b").text();
		                my_link = elem.select("a").attr("href");
		                String ftext = my_title;
		                System.out.println(ftext);
		                
		                //글 선택 후 내부 내용 크롤링
		                String url2 = my_link;
		                Connection con2 = Jsoup.connect("https://software.cbnu.ac.kr"+url2);
		                Document doc2 = con2.get();
		                //doc2.outputSettings(new Document.OutputSettings().prettyPrint(false));
		                
		                Elements EDatas = doc2.select("#articles"); //articles에서 따옴
		                
		                for(Element elem2 : EDatas) {
		                	//span
		                	String content1 = elem2.select("span").text();
		                	String content2 = elem2.select("p").text();
		                	System.out.println(content1+content2);
		                	
		                	//이미지 크롤링
		                	String img_link = elem2.select("#articles div img").attr("src");
		                	
		                	//URL img_receive = new URL(img_link);
		                	//FileOutputStream out = new FileOutputStream("C");
		                	//BufferedImage img = null;
		                	//img = ImageIO.read(img_receive);
		                	
		                	System.out.println(img_link); 
		                }
		                
		                System.out.println(i);
	            	}
	       }
	            

            
            
        }catch(IOException e) {
            builder.append("Error");
            System.out.println("에러");
        }
		
	}

}
