package com.sujan.mykitaab.ViewClass.Fragments;

import android.Manifest;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.squareup.picasso.Picasso;
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
    private static final int GALLERY_INTENT_CONSTANT =2 ;
    @BindView(R.id.floatingaction)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.floatingaction2)
    FloatingActionButton floatingActionButton2;
    @BindView(R.id.imagetowork)
    ImageView imageView;

    Intent intent;
    private TextRecognizer textRecognizer;

    Uri uriSavedImage = null;
    final String[] permision = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private Frame frame;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.albumfragment, container, false);
        ButterKnife.bind(this, view);
         textRecognizer= new TextRecognizer.Builder(getActivity()).build();
        if (!textRecognizer.isOperational()) {
            Log.w("..", "Detector dependencies are not yet available.");

            // Check for low storage.  If there is low storage, the native library will not be
            // downloaded, so detection will not become operational.
            IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = getActivity().registerReceiver(null, lowstorageFilter) != null;

            if (hasLowStorage) {
                Toast.makeText(getActivity(),"low memory", Toast.LENGTH_LONG).show();
                Log.w("...", "low memory");
            }
        }






        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @OnClick(R.id.floatingaction)
    public void OnfloatingactioniconClicked(FloatingActionButton floatingActionButton) {
        if (ContextCompat.checkSelfPermission(getActivity(),
                permision[0]) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getActivity(), permision[1]) == PackageManager.PERMISSION_GRANTED) {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                File imagesFolder = new File(Environment.getExternalStorageDirectory(), "MyImages");
                imagesFolder.mkdirs();

                File image = new File(imagesFolder, "QR_" + timeStamp + ".png");
                uriSavedImage = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", image);


                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                startActivityForResult(intent, REQUEST_CODE_FOR_CAMERA);
            }


        } else {

            if (shouldShowRequestPermissionRationale(permision[0])) {

                ActivityCompat.requestPermissions(getActivity(),permision, REQUEST_CODE_FOR_CAMERA);


            }
            if (shouldShowRequestPermissionRationale(permision[1])) {
                ActivityCompat.requestPermissions(getActivity(),permision, REQUEST_CODE_FOR_CAMERA);


            } else {
                ActivityCompat.requestPermissions(getActivity(),permision, REQUEST_CODE_FOR_CAMERA);
                Toast.makeText(getActivity(), "Access is not granted from user", Toast.LENGTH_LONG).show();
            }


        }

    }

    @OnClick(R.id.floatingaction2)
    public void onFloatingActionButton2Clicked(){
        Log.e("....","gallery clicked");
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_INTENT_CONSTANT);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.e("...", "inside onrequest permission");
//        if(requestCode==REQUEST_CODE_FOR_CAMERA &&
//               permissions[0]==Manifest.permission.CAMERA &&
//               permissions[1]==Manifest.permission.READ_EXTERNAL_STORAGE &&
//               grantResults[0]==PackageManager.PERMISSION_GRANTED &&
//               grantResults[1]==PackageManager.PERMISSION_GRANTED) {
//           intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//           intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
//           startActivityForResult(intent, REQUEST_CODE_FOR_CAMERA);
//
//
//
//       }
//       else{
//           Toast.makeText(getActivity(),"Permission must be granted to proceed further",Toast.LENGTH_SHORT).show();
//       }
//


    }


        @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            Log.e("....","inside onActivityResult");
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == getActivity().RESULT_OK) {
                String realpath="";
                uriSavedImage=data.getData();
                String path=uriSavedImage.getPath();
                String[] projection={MediaStore.Images.Media.DATA};
                Cursor cursor=getActivity().getContentResolver().query(uriSavedImage,projection,null,null,null);
                if(cursor != null ){
                    cursor.moveToFirst();
                    int index=cursor.getColumnIndex(projection[0]);
                    realpath=cursor.getString(index);
                    cursor.close();
                }
                if (requestCode == REQUEST_CODE_FOR_CAMERA) {
                    Picasso.with(getActivity())
                            .load(uriSavedImage)
                            .placeholder(R.drawable.ic_menu_gallery)
                            .error(R.drawable.ic_menu_camera)
                            .into(imageView);


//                    Intent intent = new Intent(getActivity(), PhotoPreviewActivity.class);
//                    intent.putExtra("url", uriSavedImage);
//                    startActivity(intent);



                }
                else if (requestCode ==GALLERY_INTENT_CONSTANT) {

                    Picasso.with(getActivity())
                            .load(uriSavedImage)
                            .placeholder(R.drawable.ic_menu_gallery)
                            .error(R.drawable.ic_menu_camera)
                            .into(imageView);


                }
                Bitmap bitmap=BitmapFactory.decodeFile(realpath);
                frame=new Frame.Builder().setBitmap(bitmap).build();
                SparseArray<TextBlock> items = textRecognizer.detect(frame);
                for (int i = 0; i < items.size(); ++i) {
                    TextBlock item = items.valueAt(i);
                    Log.e("....",item.getValue());
                }


            }
        }


}
