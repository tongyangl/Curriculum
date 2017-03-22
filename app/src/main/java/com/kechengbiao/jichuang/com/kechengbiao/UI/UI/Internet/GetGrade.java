package com.kechengbiao.jichuang.com.kechengbiao.UI.UI.Internet;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 佟杨 on 2017/3/16.
 */

public class GetGrade {

    public String getgrade(String cookie,String KKsj){

        String url="http://cdjwc.ccu.edu.cn/jsxsd/kscj/cjcx_list";
        DefaultHttpClient client=new DefaultHttpClient();

        HttpPost httpPost=new HttpPost(url);
        httpPost.setHeader("Cookie",cookie);

        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("kksj", KKsj));
        formParams.add(new BasicNameValuePair("kcxz", ""));
        formParams.add(new BasicNameValuePair("kcmc", ""));
        formParams.add(new BasicNameValuePair("xsfs", "all"));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, HTTP.UTF_8));

            HttpResponse response=client.execute(httpPost);
            if (response.getStatusLine().getStatusCode()==200){

                return EntityUtils.toString(response.getEntity());
            }else {

                return null;
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




}
