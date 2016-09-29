package curriculum.jichuang.tongyang.com.curriculum;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tongyang on 2016/9/20.
 */
public class KB {

    String url = "http://cdjwc.ccu.edu.cn/jsxsd/xskb/xskb_list.do";

   String GetClass(String cookieStore) {
;
        String kb=GetString.getString(url, cookieStore);

        Document document = Jsoup.parse(kb);


        return kb;

    }
    String EveryClass(String cookie,String zc,String xnxq01id){

        String kburl="http://cdjwc.ccu.edu.cn/jsxsd/xskb/xskb_list.do?Ves632DSdyV=NEW_XSD_PYGL";
        DefaultHttpClient client=new DefaultHttpClient();
        HttpPost post=new HttpPost(kburl);
        post.setHeader("Cookie",cookie);
        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("cj0701id", ""));
        formParams.add(new BasicNameValuePair("zc", zc));
        formParams.add(new BasicNameValuePair("xnxq01id", "2016-2017-1"));

        try {
            post.setEntity(new UrlEncodedFormEntity(formParams, HTTP.UTF_8));
            HttpResponse response=client.execute(post);
            if (response.getStatusLine().getStatusCode()==200){

               String s= EntityUtils.toString(response.getEntity());
                return  s;
            }

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return  null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
        return null;
    }

}
