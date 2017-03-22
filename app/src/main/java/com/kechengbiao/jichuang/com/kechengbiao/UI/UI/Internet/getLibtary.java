package com.kechengbiao.jichuang.com.kechengbiao.UI.UI.Internet;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by tongyang on 2016/10/12.
 */

public class getLibtary {

     public String get(String cookie){
         String url="http://cdjwc.ccu.edu.cn/jsxsd/grxx/xsxx?Ves632DSdyV=NEW_XSD_XJCJ";
         HttpClient httpClient=new DefaultHttpClient();
         HttpGet httpGet=new HttpGet(url);
         httpGet.setHeader("Cookie",cookie);
         try {
             HttpResponse httpResponse=httpClient.execute(httpGet);
             if (httpResponse.getStatusLine().getStatusCode()==200){

                 HttpEntity entity=httpResponse.getEntity();
               String library = EntityUtils.toString(entity);
                 return  library;
             }else {

                 return null;
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
            return null;
     }
}
