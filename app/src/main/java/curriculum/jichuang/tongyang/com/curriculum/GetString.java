package curriculum.jichuang.tongyang.com.curriculum;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by tongyang on 2016/9/24.
 */

public class GetString {
    public static  String getString(String url,String cookieStore){
        HttpGet get=new HttpGet( url);

        DefaultHttpClient httpClient=new DefaultHttpClient();
        get.setHeader("Cookie",cookieStore);
      //  httpClient.setCookieStore(cookieStore);
        try {
            HttpResponse response=httpClient.execute(get);
            if (response.getStatusLine().getStatusCode()==200){
                 String a=EntityUtils.toString(response.getEntity());
                Log.d("eee",a);
                return a;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  null;
    }

}
