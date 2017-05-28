package com.pangge.fortest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iuuu on 16/10/12.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener{

    private Button buttonOne;
    private Button buttonTwo;
    private Button buttonThree;
    private Button buttonFour;

    private ViewPager mViewPager;
    private List<Fragment> mList;

    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private ThreeFragment threeFragment;
    private FourFragment fourFragment;


    private ImageView imageviewOvertab;
    private int screenWidth;
    private int currenttab=-1;


    private FragmentPagerAdapter mAdapter;

    //first commit
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonOne = (Button)findViewById(R.id.btn1);
        buttonTwo = (Button)findViewById(R.id.btn2);
        buttonThree = (Button)findViewById(R.id.btn3);
        buttonFour = (Button)findViewById(R.id.btn4);

        buttonOne.setOnClickListener(this);
        buttonTwo.setOnClickListener(this);
        buttonThree.setOnClickListener(this);
        buttonFour.setOnClickListener(this);

        mViewPager = (ViewPager)findViewById(R.id.viewpager);

        mList = new ArrayList<Fragment>();
        oneFragment = new OneFragment();
        twoFragment = new TwoFragment();
        threeFragment = new ThreeFragment();
        fourFragment = new FourFragment();
        mList.add(oneFragment);
        mList.add(twoFragment);
        mList.add(threeFragment);
        mList.add(fourFragment);

        screenWidth = getResources().getDisplayMetrics().widthPixels;

        buttonTwo.measure(0,0);
        imageviewOvertab = (ImageView)findViewById(R.id.imgv_overtab);
        RelativeLayout.LayoutParams imageParams=new RelativeLayout.LayoutParams(screenWidth/4,buttonTwo.getMeasuredHeight());
        imageParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        imageviewOvertab.setLayoutParams(imageParams);


        mViewPager.setAdapter(new MyFrageStatePagerAdapter(
                getSupportFragmentManager()));




    }

    class MyFrageStatePagerAdapter extends FragmentStatePagerAdapter {
        public MyFrageStatePagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);

            int currentItem = mViewPager.getCurrentItem();
            if(currentItem == currenttab){
                return;
            }
            imageMove(mViewPager.getCurrentItem());
            currenttab=mViewPager.getCurrentItem();
        }
    }



    private void imageMove(int moveTotab){
        int startPosition = 0;
        int moveToPosition = 0;

        startPosition = currenttab*(screenWidth/4);
        moveToPosition = moveTotab*(screenWidth/4);

        TranslateAnimation translateAnimation = new TranslateAnimation(startPosition,moveToPosition,0,0);
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(200);
        imageviewOvertab.startAnimation(translateAnimation);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                changeView(0);
                break;
            case R.id.btn2:
                changeView(1);
                break;
            case R.id.btn3:
                changeView(2);
                break;
            case R.id.btn4:
                changeView(3);
                break;
            default:
                break;
        }

    }

    private void changeView(int desTab){

        mViewPager.setCurrentItem(desTab,true);
    }
}
