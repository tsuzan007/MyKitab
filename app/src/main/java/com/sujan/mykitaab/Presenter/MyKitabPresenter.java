package com.sujan.mykitaab.Presenter;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

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
    public void onSignupClicked() {



    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        final GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("LoginActivity", response.toString());

                        // Application code
                        try {
                            String email = object.getString("email");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            String birthday = object.getString("birthday"); // 01/31/1980 format
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
         new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "me?fields=friendlists{name}",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {

                        Log.v("LoginActivity", response.toString());
                        JSONObject jsonObject=response.getJSONObject();

                    }
                }
        ).executeAsync();


        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");

        request.setParameters(parameters);
        request.executeAsync();


    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }
}
