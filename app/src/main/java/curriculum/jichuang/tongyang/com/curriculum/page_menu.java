package curriculum.jichuang.tongyang.com.curriculum;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by tongyang on 2016/10/7.
 */

public class page_menu extends FragmentActivity {
    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private RadioButton Radio_kb, Radio_grade, Radio_library, Radio_person;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuview);
        ConnectivityManager con = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if (wifi|internet){

            Toast.makeText(this,"网络连接正常",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"网络连接异常常",Toast.LENGTH_SHORT).show();
        }
        initView();
    }
    private void initView() {
        /**
         * RadioGroup部分
         */
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        Radio_kb = (RadioButton) findViewById(R.id.radio_kb);
        Radio_grade = (RadioButton) findViewById(R.id.radio_grade);
        Radio_library = (RadioButton) findViewById(R.id.radio_library);
        Radio_person = (RadioButton) findViewById(R.id.radio_yourself);
        //RadioGroup选中状态改变监听
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_kb:
                        /**
                         * setCurrentItem第二个参数控制页面切换动画
                         * true:打开/false:关闭
                         */

                        viewPager.setCurrentItem(0, false);
                        break;
                    case R.id.radio_grade:
                        viewPager.setCurrentItem(1, false);
                        break;
                    case R.id.radio_library:
                        viewPager.setCurrentItem(2, false);
                        break;
                    case R.id.radio_yourself:
                        viewPager.setCurrentItem(3, false);
                        break;
                }
            }
        });

        /**
         * ViewPager部分
         */

        //获取当前时间为本月的第几周

        viewPager = (ViewPager) findViewById(R.id.viewpage);
        KBFragment kbFragment = new KBFragment();
        GradeFragment gradeFragment = new GradeFragment();
        LibraryFragment libraryFragment = new LibraryFragment();
        PersonFragment personFragment = new PersonFragment();
        List<Fragment> alFragment = new ArrayList<Fragment>();
        alFragment.add(kbFragment);
        alFragment.add(gradeFragment);
        alFragment.add(libraryFragment);
        alFragment.add(personFragment);
        //ViewPager设置适配器
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), alFragment));
        //ViewPager显示第一个Fragment
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);
        //ViewPager页面切换监听
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        radioGroup.check(R.id.radio_kb);
                        break;
                    case 1:
                        radioGroup.check(R.id.radio_grade);
                        break;
                    case 2:
                        radioGroup.check(R.id.radio_library);
                        break;
                    case 3:
                        radioGroup.check(R.id.radio_yourself);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // 过滤按键动作
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

            moveTaskToBack(true);

        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {

        moveTaskToBack(true);
        super.onBackPressed();
    }
}
