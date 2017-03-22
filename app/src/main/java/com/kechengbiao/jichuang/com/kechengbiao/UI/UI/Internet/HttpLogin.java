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



    private static String encodeInp(String input) {
        String keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
        String output = "";
        int chr1, chr2, chr3;
        int enc1, enc2, enc3, enc4;
        int i = 0;
        chr1 = chr2 = chr3 = enc1 = enc2 = enc3 = enc4 = 0;
        do {

            chr1 = input.codePointAt(i++);
            enc1 = chr1 >> 2;
            if (i < input.length()) {
                chr2 = input.codePointAt(i++);
                enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                if (i < input.length()) {
                    chr3 = input.codePointAt(i++);
                    enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                    enc4 = chr3 & 63;
                } else {
                    enc3 = enc4 = 64;
                }
            } else {
                enc2 = (chr1 & 3) << 4;
                enc3 = enc4 = 64;
            }


            output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2) + keyStr.charAt(enc3) + keyStr.charAt(enc4);
            chr1 = chr2 = chr3 = enc1 = enc2 = enc3 = enc4 = 0;


        } while (i < input.length());
        Log.d("qweqwe", output);
        return output;

    }

}
