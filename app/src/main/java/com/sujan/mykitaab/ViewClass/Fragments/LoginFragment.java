package com.sujan.mykitaab.ViewClass.Fragments;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;

import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.sujan.mykitaab.MainActivity;
import com.sujan.mykitaab.POJOClasses.FeedClass;
import com.sujan.mykitaab.POJOClasses.MessageEvent;
import com.sujan.mykitaab.POJOClasses.User_WithFacebook;
import com.sujan.mykitaab.Presenter.MVPContracts;
import com.sujan.mykitaab.Presenter.MyKitabPresenter;
import com.sujan.mykitaab.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by macbookpro on 4/14/17.
 */

public class LoginFragment extends Fragment implements MVPContracts.PublishToView{
    private CallbackManager callbackManager;
    private MyKitabPresenter myKitabPresenter;


    @BindView(R.id.edittext_username)
    EditText username;
    @BindView(R.id.edittext_password)
    EditText password;
    @BindView(R.id.button_signin)
    Button signin;
    @BindView(R.id.button_facebooklogin)
    LoginButton facebooklogin;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myKitabPresenter=new MyKitabPresenter(this);


    }


    @Override
    public void onResume() {
        super.onResume();
        if (facebooklogin.getText().toString().equals("Log out")) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }


    }





    /*
        Code to get hash key
        --------------------
        try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo(
                    "com.sujan.mykitaab",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash........:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);
        ButterKnife.bind(this, view);
        facebooklogin.setReadPermissions("public_profile", "user_birthday", "email", "user_posts", "user_friends", "read_custom_friendlists");
        facebooklogin.setFragment(this);

        callbackManager = CallbackManager.Factory.create();
        return view;
    }


    @OnClick({R.id.button_facebooklogin, R.id.button_signin})
    public void onClickbutton(View view) {
        if (view.getId() == R.id.button_facebooklogin) {
            callbackManager = CallbackManager.Factory.create();
            facebooklogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    myKitabPresenter.onSuccess(loginResult);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);


                }

                @Override
                public void onCancel() {
                    myKitabPresenter.onCancel();

                }

                @Override
                public void onError(FacebookException error) {
                    myKitabPresenter.onError(error);

                }
            });


        } else if (view.getId() == R.id.button_signin) {
            String name = username.getText().toString();
            if (!TextUtils.isEmpty(name)) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("username", name);
                startActivityForResult(intent,1);

            } else {
                DialogFragment dialogFragment = new DialogFragment();
                dialogFragment.show(getFragmentManager(), "message dialog");
            }


        }
    }

    @OnClick(R.id.textview_signup)
    public void onSignupClicked(TextView textView) {
        /*
        get userid, name, email address from user and pass it to the function
         */
        myKitabPresenter.onSignupClicked();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void publish_userInfo(User_WithFacebook user) {

    }

    @Override
    public void publish_feeds(FeedClass feedClass) {
    }



}
