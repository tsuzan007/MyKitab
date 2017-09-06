package com.sujan.mykitaab;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;

import com.facebook.login.LoginManager;
import com.sujan.mykitaab.HelperClass.MessageEvent;
import com.sujan.mykitaab.HelperClass.User_WithFacebook;
import com.sujan.mykitaab.Presenter.MyKitabPresenter;
import com.sujan.mykitaab.Presenter.PresenterContract;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements PresenterContract.PublishView {
    @BindView(R.id.app_toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.change_layout)
    Button changelayoutbutton;
    @BindView(R.id.logout)
    Button logout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    MyKitabPresenter myKitabPresenter;
    MessageEvent messageEvent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        myKitabPresenter = new MyKitabPresenter(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);


        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }

    @OnClick(R.id.change_layout)
    public void changelayoutclicked(Button button) {

        EventBus.getDefault().post(new MessageEvent("hello"));

    }

    @OnClick(R.id.logout)
    public void onlogoutButtonClicked(Button button) {
        LoginManager.getInstance().logOut();
        finish();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        String name = "";
        name = intent.getStringExtra("username");
        if (TextUtils.isEmpty(name)) {

            navigationView.getMenu().getItem(0).setTitle(name);

        }
        navigationView.getMenu().getItem(0).setTitle(name);
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void publish_userInfo(User_WithFacebook user) {


    }

    @Subscribe
    public void updatenavigation(User_WithFacebook user_withFacebook) {
        navigationView.getMenu().getItem(0).setTitle(user_withFacebook.getName());
        navigationView.getMenu().getItem(1).setTitle(user_withFacebook.getEmail_id());
        navigationView.getMenu().getItem(2).setTitle(user_withFacebook.getDate_of_birth());


    }


}
