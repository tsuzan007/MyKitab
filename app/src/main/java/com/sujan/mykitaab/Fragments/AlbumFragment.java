package com.sujan.mykitaab.Fragments;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.sujan.mykitaab.PhotoPreviewActivity;
import com.sujan.mykitaab.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by macbookpro on 4/16/17.
 */

public class AlbumFragment extends Fragment {

    private static final int REQUEST_CODE_FOR_CAMERA = 1;
    @BindView(R.id.floatingaction)
    FloatingActionButton floatingActionButton;
    ImageView imageView;

    Uri uriSavedImage = null;
    final String[] permision = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.albumfragment, container, false);


        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @OnClick(R.id.floatingaction)
    public void OnfloatingactioniconClicked(FloatingActionButton floatingActionButton) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "MyImages");
        imagesFolder.mkdirs();

        File image = new File(imagesFolder, "QR_" + timeStamp + ".png");
        uriSavedImage = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", image);


        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {


                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                startActivityForResult(intent, REQUEST_CODE_FOR_CAMERA);
            }


        } else {

            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                Toast.makeText(getActivity(), "Access is not granted from user", Toast.LENGTH_LONG).show();


            }
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(getActivity(), "Access is not granted from user", Toast.LENGTH_LONG).show();


            } else {
                requestPermissions(permision, REQUEST_CODE_FOR_CAMERA);
            }


        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_FOR_CAMERA) {
            if (resultCode == getActivity().RESULT_OK) {

                Intent intent = new Intent(getActivity(), PhotoPreviewActivity.class);
                intent.putExtra("url", uriSavedImage);
                startActivity(intent);


            }
        }
    }
}
