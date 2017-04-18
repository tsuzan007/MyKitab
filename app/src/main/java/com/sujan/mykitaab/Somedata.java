package com.sujan.mykitaab;

/**
 * Created by macbookpro on 4/17/17.
 */

public class  Somedata {
    private static boolean IS_FACEBOOK_LOGIN_SUCCESS=false;

    public static boolean isFacebookLoginSuccess() {
        return IS_FACEBOOK_LOGIN_SUCCESS;
    }

    public static void setIsFacebookLoginSuccess(boolean isFacebookLoginSuccess) {
        IS_FACEBOOK_LOGIN_SUCCESS = isFacebookLoginSuccess;
    }
}
