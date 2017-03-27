package com.kechengbiao.jichuang.com.kechengbiao.UI.UI;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * Created by 佟杨 on 2017/3/9.
 */

public class Util extends Activity {
    public static String URL_LIBRARY_SEARCH="http://219.149.214.106:8087/opac/openlink.php?strSearchType=title&match_flag=forward&historyCount=1&strText=";
    public static String URL_LIBRARY_SEARCH1="&doctype=ALL&with_ebook=on&displaypg=20&showmode=list&sort=CATA_DATE&orderby=desc&dept=ALL";
    public String getZc(String str) {
        if (str.contains("(双周)")){
            String zc="";
           String newstr= str.replace("(双周)","");
            String[] z = newstr.split("-");
            int a = Integer.parseInt(z[0]);
            zc = a + "";
            int b = Integer.parseInt(z[1]);
            for (int i = 0; i < b - a; i++) {
               if ((a+i)%2==0){
                   zc = zc + "." + (a + i);
               }
            }
           return zc;
        }else if (str.contains("(单周)")){
            String zc="";
          String newstr= str.replace("(单周)","");
            String[] z = newstr.split("-");
            int a = Integer.parseInt(z[0]);
            zc = a + "";
            int b = Integer.parseInt(z[1]);
            for (int i = 0; i < b - a; i++) {
                if ((a+i)%2!=0){
                    zc = zc + "." + (a + i);
                }
            }
            return zc;

        }else {

            String newstr = str.replace("(周)", "");

            if (newstr.contains(",")) {
                String zc = "";
                String string[] = newstr.split(",");
                Log.d("sss",string[0]+"sss"+string[1]);
                for (int i = 0; i < string.length; i++) {
                    if (string[i].contains("-")) {
                        String[] z = string[i].split("-");
                        int a = Integer.parseInt(z[0]);
                        int b = Integer.parseInt(z[1]);
                        zc = a + "";
                        for (int j = 1; j <= b - a; j++) {

                            zc = zc + "." + (a + j);
                        }
                    } else {

                        zc = string[i] + "." + zc;

                    }
                }
                return zc;
            } else {
                if (newstr.contains("-")) {
                    String zc = "";
                    String[] z = newstr.split("-");
                    int a = Integer.parseInt(z[0]);
                    zc = a + "";
                    int b = Integer.parseInt(z[1]);
                    for (int i = 1; i <= b - a; i++) {

                        zc = zc + "." + (a + i);
                    }
                    return zc;
                } else {
                    return str;
                }

            }
        }
    }

    public static boolean isNumeric(String str){
        for (int i = 0; i < str.length(); i++){
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
