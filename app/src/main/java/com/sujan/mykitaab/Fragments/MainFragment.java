package com.sujan.mykitaab.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sujan.mykitaab.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by macbookpro on 4/16/17.
 */

public class MainFragment extends Fragment {

    @BindView(R.id.myViewPager) ViewPager viewPager;
    @BindView(R.id.myTablayout) TabLayout tabLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.main_fragment,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        createViewPager();

    }


    public void createViewPager(){
        tabLayout.setupWithViewPager(viewPager);
        MyFragmentAdapter myFragmentAdapter=new MyFragmentAdapter(getFragmentManager());
        viewPager.setAdapter(myFragmentAdapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if(tab.getPosition()==0) {
//                    Toast.makeText(getActivity(), "Tab 1 is selected", Toast.LENGTH_LONG).show();
//                }
//                else
//                    Toast.makeText(getActivity(),"Tab 2 is selected",Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });






    }

    public class MyFragmentAdapter extends FragmentPagerAdapter{
        String[] fragmentname={"FriendList","Albums"};

        @Override
        public CharSequence getPageTitle(int position) {
                    return fragmentname[position];


        }

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    Fragment friendFragment=new Friendlistfragment();
                    return friendFragment;
                case 1:
                    Fragment AlbumFragment=new AlbumFragment();
                    return AlbumFragment;
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return fragmentname.length;
        }
    }
}
