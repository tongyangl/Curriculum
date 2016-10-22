package curriculum.jichuang.tongyang.com.curriculum;

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
 * Created by tongyang on 2016/10/4.
 */

public class getGrade {

    public String getgrade(String cookie,String KKsj,String kcxz,String kcfmc,String xsfs){

        String url="http://cdjwc.ccu.edu.cn/jsxsd/kscj/cjcx_list";
        DefaultHttpClient client=new DefaultHttpClient();

        HttpPost httpPost=new HttpPost(url);
        httpPost.setHeader("Cookie",cookie);

        switch (kcxz){
            case "所有": kcxz="";break;
            case "---请选择---": kcxz="";break;
            case "公共课": kcxz="01";break;
            case "公共公共基础课课": kcxz="02";break;
            case "专业基础课": kcxz="03";break;
            case "专业课": kcxz="04";break;
            case "专业选修课": kcxz="05";break;
            case "公共选修课": kcxz="06";break;
            case "其他": kcxz="07";break;
            case "学科基础课程": kcxz="08";break;
            case "专业及专业特色课程": kcxz="09";break;
            case "素质教育课程": kcxz="10";break;
            case "实践环节课程": kcxz="11";break;

        }
        Log.d("asdasdasd", KKsj+"aa");
        Log.d("asdasdasd", kcxz+"aa");
        Log.d("asdasdasd", kcfmc+"aa");
        Log.d("asdasdasd", xsfs+"aa");
        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("kksj", KKsj));
        formParams.add(new BasicNameValuePair("kcxz", kcxz));
        formParams.add(new BasicNameValuePair("kcmc", kcfmc));
        formParams.add(new BasicNameValuePair("xsfs", xsfs));

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
