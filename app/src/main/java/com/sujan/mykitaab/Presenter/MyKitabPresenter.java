package com.sujan.mykitaab.Presenter;

import android.util.Log;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.sujan.mykitaab.Model.User_WithFacebook;
import com.sujan.mykitaab.Model.User_Without_Facebook;
import com.sujan.mykitaab.Somedata;

/**
 * Created by macbookpro on 4/17/17.
 */

public class MyKitabPresenter implements FacebookCallback<LoginResult>,PresenterContract.LoginInfoInputToPresenter{


    PresenterContract.LoginInfoInputToPresenter loginInfo;

    private PresenterContract.PublishView publishreport;



    public MyKitabPresenter(PresenterContract.PublishView publishreport) {
        this.publishreport=publishreport;

    }

    public MyKitabPresenter(PresenterContract.LoginInfoInputToPresenter loginInfoInputToPresenter){
        this.loginInfo=loginInfoInputToPresenter;
    }



    //when login is success, message onSuccess is called from the fragment
    @Override
    public void onSuccess(LoginResult loginResult) {
        Somedata.setIsFacebookLoginSuccess(true);
       Log.e("......","onSuccess");



    }

    //when login is cancelled/denied, message oncalled is called from the fragment
    @Override
    public void onCancel() {
        Somedata.setIsFacebookLoginSuccess(false);
        Log.e("......","onCancel");

    }


    //when error occurs, message onError is called from the fragment.
    @Override
    public void onError(FacebookException error) {
        Somedata.setIsFacebookLoginSuccess(false);
        Log.e("......","onError");

    }

    @Override
    public void onSignupClicked() {



    }
}
