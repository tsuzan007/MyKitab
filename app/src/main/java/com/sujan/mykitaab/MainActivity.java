package com.sujan.mykitaab;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sujan.mykitaab.HelperClass.User_WithFacebook;
import com.sujan.mykitaab.Presenter.MyKitabPresenter;
import com.sujan.mykitaab.Presenter.PresenterContract;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity  implements PresenterContract.PublishView{
    @BindView(R.id.app_toolbar) Toolbar toolbar;
    @BindView(R.id.drawer) DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view) NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    MyKitabPresenter myKitabPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        myKitabPresenter = new MyKitabPresenter(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent=getIntent();
        String name=intent.getStringExtra("username");
        if(name.isEmpty()){
            navigationView.getMenu().getItem(0).setTitle(name);

        }
        navigationView.getMenu().getItem(0).setTitle(name);
    }

    @Override
    public void publish_userInfo(User_WithFacebook user) {


    }
}
