package com.sujan.mykitaab.Presenter;

import com.facebook.FacebookCallback;
import com.facebook.login.LoginResult;
import com.sujan.mykitaab.Model.User_WithFacebook;
import com.sujan.mykitaab.Model.User_Without_Facebook;

/**
 * Created by macbookpro on 4/17/17.
 */

public interface PresenterContract {
        //presenter will send updates to View (Fragment or Activity)
     interface PublishView{
            void publish_userInfo(User_WithFacebook user);


    }
    //presenter will receive data from the view
    interface LoginInfoInputToPresenter{
        void onSignupClicked();

    }
    //presenter will communicate with the Model
    interface getDataFromService{


    }
  //model will update the presenter
    interface updatethepresenter{

    }




}
