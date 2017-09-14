package com.sujan.mykitaab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;

import com.facebook.login.LoginManager;
import com.sujan.mykitaab.POJOClasses.MessageEvent;
import com.sujan.mykitaab.POJOClasses.User_WithFacebook;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
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
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private User_WithFacebook user_withFacebook;
    protected Intent intent;
    private SharedPreferences sharedPreferences;
    private SharedPreferences userstatus;
    private SharedPreferences.Editor editor;
    private SharedPreferences.Editor editor2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            user_withFacebook = savedInstanceState.getParcelable("user");
        }
        ButterKnife.bind(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        //calling sync state is necessary or else hamburger icon wont show up
        actionBarDrawerToggle.syncState();


    }

    @Override
    protected void onStart() {
        super.onStart();
        intent = getIntent();
        sharedPreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        userstatus=getSharedPreferences("userstatus",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor2=userstatus.edit();
        EventBus.getDefault().register(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(0).setTitle(sharedPreferences.getString("name", "Name"));
        navigationView.getMenu().getItem(1).setTitle(sharedPreferences.getString("emailid", "email"));
        navigationView.getMenu().getItem(2).setTitle(sharedPreferences.getString("dob", "xx-xx-xxxx"));


    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    @OnClick(R.id.change_layout)
    public void changelayoutclicked(Button button) {
        EventBus.getDefault().post(new MessageEvent("hello"));
    }

    @OnClick(R.id.logout)
    public void onlogoutButtonClicked(Button button) {
        LoginManager.getInstance().logOut();
        editor2.putBoolean("userStatus",false);
        editor2.commit();
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("user", user_withFacebook);
        super.onSaveInstanceState(outState);
    }


    @Subscribe
    public void updatenavigation(User_WithFacebook user_withFacebook) {
        this.user_withFacebook = user_withFacebook;
        editor.putString("name", user_withFacebook.getName());
        editor.putString("emailid", user_withFacebook.getEmail_id());
        editor.putString("dob", user_withFacebook.getDate_of_birth());
        editor.commit();
        navigationView.getMenu().getItem(0).setTitle(user_withFacebook.getName());
        navigationView.getMenu().getItem(1).setTitle(user_withFacebook.getEmail_id());
        navigationView.getMenu().getItem(2).setTitle(user_withFacebook.getDate_of_birth());

    }


}
