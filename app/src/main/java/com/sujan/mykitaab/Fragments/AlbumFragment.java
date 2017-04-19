package com.sujan.mykitaab.Fragments;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.sujan.mykitaab.R;

import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by macbookpro on 4/16/17.
 */

public class AlbumFragment extends Fragment {

    @BindView(R.id.floatingaction)FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.albumfragment,container,false);
        Toast.makeText(getActivity(),"Album is selected",Toast.LENGTH_LONG).show();
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick(R.id.floatingaction)
    public void OnfloatingactioniconClicked(FloatingActionButton floatingActionButton){

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {

            if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                requestPermissions(new String[]{Manifest.permission.CAMERA},1);
                Toast.makeText(getActivity(),"access denied",Toast.LENGTH_SHORT).show();

            }
            else{
                requestPermissions(new String[]{Manifest.permission.CAMERA},1);

            }


        }
        else {
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,1);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
