package com.sujan.mykitaab.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.sujan.mykitaab.MainActivity;
import com.sujan.mykitaab.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.R.attr.bitmap;
import static android.content.Context.LOCATION_SERVICE;
import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;

/**
 * Created by macbookpro on 4/18/17.
 */

public class CameraPreviewFragment extends Fragment  implements LocationListener {
    Uri uriSavedImage;
    ImageView imageView;
    String mylocation = "Default";
    LocationManager locationManager;
    Geocoder geocoder;
    android.location.Location location;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photopreview_fragment, container, false);
        imageView = (ImageView) view.findViewById(R.id.camera_imageview);
        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        geocoder=new Geocoder(getActivity());

    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getActivity().getIntent();
        uriSavedImage = intent.getParcelableExtra("url");
        final InputStream imageStream;
        try {
            imageStream = getActivity().getContentResolver().openInputStream(uriSavedImage);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            ExifInterface exif = new ExifInterface(uriSavedImage.getPath());
//            int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//            int rotationInDegrees = exifToDegrees(rotation);
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
//            if (rotation != 0f) {
//                matrix.postRotate(rotationInDegrees);}

            Bitmap adjustedBitmap = Bitmap.createBitmap(selectedImage, 0, 0, selectedImage.getWidth(), selectedImage.getHeight(), matrix, true);
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)){
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                        Log.e("....","in should show request");
                    }
                    else{
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    }
                }
            }
            else {
               location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5,this);

            }
            double lat=location.getLatitude();
            double lng=location.getLongitude();
            List<Address> locations=geocoder.getFromLocation(lat,lng,1);
            try {
                mylocation = locations.get(0).getLocality();
            }catch (Exception e){
                mylocation=locations.get(0).getCountryName();
            }

            Canvas canvas = new Canvas(adjustedBitmap);
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setTextSize(50);
            canvas.drawText(mylocation, 20, 100, paint);


            imageView.setImageBitmap(adjustedBitmap);
            imageStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("...aaa..", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 90;
    }



    @Override
    public void onLocationChanged(Location location) {
        double lat=location.getLatitude();
        double lng=location.getLongitude();
       try {
           List<Address> locations=geocoder.getFromLocation(lat,lng,1);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
