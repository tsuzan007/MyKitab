package com.sujan.mykitaab;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LogInActivity extends Activity {
    protected Fragment loginfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        loginfragment = getFragmentManager().findFragmentById(R.id.fragment1);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");

    }
}
