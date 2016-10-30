package org.sang.redpackets;

import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.p1);
        list.add(R.drawable.p2);
        list.add(R.drawable.p3);
        list.add(R.drawable.p4);
        VpAdapter adapter = new VpAdapter(list, this);
        viewPager.setAdapter(adapter);
        final TextView tv2 = (TextView) findViewById(R.id.tv2);
        tv2.setTypeface(Typeface.createFromAsset(getAssets(),"mycustom.ttf"));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 3) {
                    tv2.setVisibility(View.GONE);
                }else {
                    tv2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
