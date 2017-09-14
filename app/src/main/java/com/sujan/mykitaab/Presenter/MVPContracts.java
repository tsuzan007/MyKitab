package com.sujan.mykitaab.Presenter;

import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.sujan.mykitaab.POJOClasses.FeedClass;
import com.sujan.mykitaab.POJOClasses.User_WithFacebook;

/**
 * Created by macbookpro on 9/6/17.
 */

public interface MVPContracts {

        //presenter will send updates to View (Fragment or Activity)
        interface PublishToView {
            void publish_userInfo(User_WithFacebook user);
            void publish_feeds(FeedClass feedClass);

        }


        //presenter will receive data from the view
        interface ViewPresenterContract {
            void onSignupClicked();
            void onSuccess(LoginResult loginResult);
            void onLoadFeeds();


        }

        interface FeedsPresenterContract{



        }
        interface ModelPresenterContract{

        }

        interface RequestModel{
            void getUserInfo();
            void getFeedsList();
        }


    }

