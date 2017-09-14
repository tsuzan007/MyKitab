package com.sujan.mykitaab.Presenter;

import android.os.Looper;
import android.util.Log;


import com.facebook.FacebookException;

import com.facebook.login.LoginResult;
import com.sujan.mykitaab.Model.Model;
import com.sujan.mykitaab.POJOClasses.FeedClass;
import com.sujan.mykitaab.POJOClasses.MessageEvent;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/**
 * Created by macbookpro on 4/17/17.
 */

public class MyKitabPresenter implements
        MVPContracts.ViewPresenterContract,
        MVPContracts.ModelPresenterContract,
        MVPContracts.FeedsPresenterContract{




    private MVPContracts.PublishToView publishreport;
    private MVPContracts.RequestModel requestModel;



    public MyKitabPresenter(MVPContracts.PublishToView publishreport) {
        this.publishreport = publishreport;
        this.requestModel=new Model(this);

    }





    //when login is success, message onSuccess is called from the fragment


    @Override
    public void onSignupClicked() {



    }



    public void onSuccess(LoginResult loginResult) {
        requestModel.getUserInfo();

    }



    @Override
    public void onLoadFeeds() {

        requestModel.getFeedsList();

    }


    @Subscribe
    public void requestedFeedReady(FeedClass feedClass){
        publishreport.publish_feeds(feedClass);
    }

    @Subscribe
    public void FeedLoaded(MessageEvent m){
        Log.e("...",m.message.toString());



    }

    public void onStop() {
        EventBus.getDefault().unregister(this);
    }

    public void start() {
        EventBus.getDefault().register(this);
    }
}
