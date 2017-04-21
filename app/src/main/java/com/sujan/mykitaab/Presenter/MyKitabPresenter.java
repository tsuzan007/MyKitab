package com.sujan.mykitaab.Presenter;

import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.sujan.mykitaab.HelperClass.User_WithFacebook;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by macbookpro on 4/17/17.
 */

public class MyKitabPresenter implements FacebookCallback<LoginResult>,PresenterContract.LoginInfoInputToPresenter,PresenterContract.setLocation{


    PresenterContract.LoginInfoInputToPresenter loginInfo;

    private PresenterContract.PublishView publishreport;
    static User_WithFacebook user_withFacebook;




    public MyKitabPresenter(PresenterContract.PublishView publishreport) {
        this.publishreport=publishreport;

    }

    public MyKitabPresenter(PresenterContract.LoginInfoInputToPresenter loginInfoInputToPresenter){
        this.loginInfo=loginInfoInputToPresenter;
    }



    //when login is success, message onSuccess is called from the fragment


    @Override
    public void onSignupClicked() {



    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "me?fields=id,name,email,birthday",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        if(response.getError()==null) {

                            Log.v("LoginActivity", response.toString());
                            JSONObject jsonObject = response.getJSONObject();
                            try {
                                String id=jsonObject.getString("id");
                                String name=jsonObject.getString("name");
                                String email=jsonObject.getString("email");
                                String birthday=jsonObject.getString("birthday");
                               user_withFacebook=new User_WithFacebook(name,email,id,birthday);
                                EventBus.getDefault().post(user_withFacebook);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                    }
                }
        ).executeAsync();

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }


    @Override
    public void setLocation(String location) {


    }
}
