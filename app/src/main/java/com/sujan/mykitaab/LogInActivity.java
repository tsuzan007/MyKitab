package com.sujan.mykitaab;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

public class LogInActivity extends Activity {

    Fragment loginfragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        loginfragment=getFragmentManager().findFragmentById(R.id.fragment1);
    }
}
