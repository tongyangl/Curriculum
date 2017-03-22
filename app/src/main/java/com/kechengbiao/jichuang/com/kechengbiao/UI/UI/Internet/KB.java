package com.kechengbiao.jichuang.com.kechengbiao.UI.UI.Internet;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tongyang on 2016/9/20.
 */
public class KB {



  /* public String GetClass(String cookieStore) {
       String url = "http://cdjwc.ccu.edu.cn/jsxsd/xskb/xskb_list.do";
        String kb=GetString.getString(url, cookieStore);

        return kb;

    }*/
   public String EveryClass(String cookie, String xnxq01id){
        if (xnxq01id.equals("")){
            xnxq01id="2016-2017-1";
        }
        String kburl="http://cdjwc.ccu.edu.cn/jsxsd/xskb/xskb_list.do?Ves632DSdyV=NEW_XSD_PYGL";
        DefaultHttpClient client=new DefaultHttpClient();
        HttpPost post=new HttpPost(kburl);
        post.setHeader("Cookie",cookie);
        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("cj0701id", ""));
        formParams.add(new BasicNameValuePair("zc", ""));
        formParams.add(new BasicNameValuePair("xnxq01id", xnxq01id));

        try {
            post.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
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
