package com.sujan.mykitaab.POJOClasses;

import com.facebook.AccessToken;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by macbookpro on 4/18/17.
 */

public interface FacebookClient {


    @GET("endpoint?key=value&amp;access_token=app_id|app_secret")
    Call<Facebookuser> username(@Query("user") AccessToken user);

}
