package com.kechengbiao.jichuang.com.kechengbiao.UI.UI.Internet;

import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.CookieManager;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tongyang on 2016/9/14.
 */

public class HttpLogin {

    private int Code;
    private String cookieString;
    public static CookieManager cookieManager = null;
    private SharedPreferences sharedPreferences;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    CookieStore getcookie() {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://cdjwc.ccu.edu.cn/jsxsd/");
        try {
            HttpResponse response = client.execute(get);

            if (response.getStatusLine().getStatusCode() == 200) {
                Log.d("******", client.getCookieStore().toString());


                return client.getCookieStore();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String login(String username, String password) {

        //  String url = "http://cdjwc.ccu.edu.cn/jsxsd/xk/LoginToXk";
        String url = "http://cdjwc.ccu.edu.cn/jsxsd/xk/LoginToXk";
        // 根据url获得HttpPost对象
        HttpPost httpRequest = new HttpPost(url);

        // 取得默认的HttpClient
        DefaultHttpClient httpclient = new DefaultHttpClient();
        CookieStore c = getcookie();
        Log.d("//////", c.toString());
        httpclient.setCookieStore(c);

        httpclient.setRedirectHandler(new RedirectHandler() {
            @Override
            public boolean isRedirectRequested(HttpResponse httpResponse, HttpContext httpContext) {

                return false;
            }

            @Override
            public URI getLocationURI(HttpResponse httpResponse, HttpContext httpContext) throws ProtocolException {
                return null;
            }
        });
        // NameValuePair实现请求参数的封装
        String encoded;
        String Accout;
        String passwd;
        Log.d("xxxx", username);
        Log.d("xxxx", password);
        Accout = encodeInp(username);
        passwd = encodeInp(password);
        encoded = Accout + "%%%" + passwd;
        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("encoded", encoded));
        Log.d("ssss", formParams.toString());
        try {
            // 添加请求参数到请求对象
            httpRequest.setEntity(new UrlEncodedFormEntity(formParams, HTTP.UTF_8));
            // 获得响应对象
            HttpResponse httpResponse = httpclient.execute(httpRequest);

            // 判断是否请求成功
            //MDQxNDQwNzMw%%%c3N5MTk5MzA0MTA=
            // MDQxNDQwNzMw%%%c3N5MTk5MzA0MT==


            int code = httpResponse.getStatusLine().getStatusCode();
            Log.d("aaa", code + "");
            if (code == 302) {
                setCode(302);
                CookieStore mCookieStore = httpclient.getCookieStore();
                List<Cookie> cookies = mCookieStore.getCookies();
                if (cookies.isEmpty()) {
                    System.out.println("Cookies为空");
                } else {

                    // 保存cookie
                    Cookie cookie = cookies.get(0);
                    Log.d("Cookie", cookies.get(0).getName() + "=" + cookies.get(0).getValue());

                    cookieString = cookie.getName() + "=" + cookie.getValue() + "; domain=" + cookie.getDomain();

                    return cookieString;
                }

                httpRequest.abort();

            } else if (code == 200) {
                setCode(200);
                Log.d("---", "登录失败");
                return null;
            } else {
                Log.d("===", code + "");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static  String encodeInp(String s){
        String key = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
        byte[] bytes=s.getBytes();
        char[] keys=key.toCharArray();
        StringBuilder out= new StringBuilder("");
        byte b1,b2,b3;
        int c1,c2,c3,c4;
        int i=0;
        do{
            b1=bytes[i++];
            if(i==s.length()){
                c1=b1 >> 2;
                c2=(b1&3)<<4;
                c3=c4=64;
                out.append(keys[c1]);
                out.append(keys[c2]);
                out.append(keys[c3]);
                out.append(keys[c4]);
                break;
            }
            b2=bytes[i++];
            if(i==s.length()){
                c1=b1 >> 2;
                c2=((b1&3)<<4)| b2>>4;
                c3=(b2&15)<<2;
                c4=64;
                out.append(keys[c1]);
                out.append(keys[c2]);
                out.append(keys[c3]);
                out.append(keys[c4]);
                break;
            }
            b3=bytes[i++];
            c1=b1 >> 2;
            c2=((b1&3)<<4)| b2>>4;
            c3=((b2&15)<<2)|b3>>6;
            c4=b3&63;
            out.append(keys[c1]);
            out.append(keys[c2]);
            out.append(keys[c3]);
            out.append(keys[c4]);
        }while (i<s.length());
        return out.toString();
    }

}
